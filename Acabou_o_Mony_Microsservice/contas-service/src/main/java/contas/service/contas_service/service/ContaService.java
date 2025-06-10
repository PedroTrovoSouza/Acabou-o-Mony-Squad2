package contas.service.contas_service.service;


import contas.service.contas_service.dto.conta.ContaRequestDto;
import contas.service.contas_service.entity.*;
import contas.service.contas_service.enums.TipoCliente;
import contas.service.contas_service.enums.TipoConta;
import contas.service.contas_service.exception.ContaConflitoException;
import contas.service.contas_service.exception.ContaNaoEncontradaException;
import contas.service.contas_service.exception.ContaSalarioNaoPermitidaParaPJException;
import contas.service.contas_service.exception.TipoClienteInvalidoException;
import contas.service.contas_service.mapper.ContaMapper;
import contas.service.contas_service.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;
    private final ClienteService clienteService;

    private final RabbitTemplate rabbitTemplate;

    public List<Conta> listarContas(){
        return contaRepository.findAll();
    }

    public Conta salvarContaComValidacao(Conta conta, Cliente cliente){
        if (contaRepository.existsByTipoContaAndCliente(conta.getTipoConta(), cliente)){
            throw new ContaConflitoException("Esse Cliente já possui uma conta desse tipo.");
        }
        conta.setIsAtiva(true);
        conta.setCliente(cliente);
        conta.setSaldo(BigDecimal.ZERO);
        return contaRepository.save(conta);
    }

    public Conta validarTipoDeConta(Conta conta, Cliente cliente){
        if (conta.getTipoConta() == TipoConta.CONTA_CORRENTE){
            conta.setLimiteCredito(cliente.getPerfilEconomico().limite);
        }else if(conta.getTipoConta() == TipoConta.CONTA_POUPANCA){
            conta.setIsDebito(true);
            conta.setIsCredito(false);
            conta.setLimiteCredito(BigDecimal.ZERO);
        } else {
            if (cliente instanceof PessoaFisica){
                conta.setIsDebito(false);
                conta.setIsCredito(false);
                conta.setLimiteCredito(BigDecimal.ZERO);
            }else {
                throw new ContaSalarioNaoPermitidaParaPJException("Pessoa Jurídica não pode abrir conta salário.");
            }
        }
        return conta;
    }

    public Conta abrirConta(ContaRequestDto dto) {
        Cliente cliente;
        if (dto.tipoCliente().equals(TipoCliente.PESSOA_FISICA)) {
            cliente = clienteService.buscarPessoaFisicaPorId(dto.idCliente());
        } else if (dto.tipoCliente().equals(TipoCliente.PESSOA_JURIDICA)) {
            cliente = clienteService.buscarPessoaJuridicaPorId(dto.idCliente());
        } else {
            throw new TipoClienteInvalidoException("Tipo de cliente inválido.");
        }

        Conta conta = ContaMapper.toEntity(dto, cliente);
        Conta contaValidada = validarTipoDeConta(conta, cliente);
        Conta contaSalva = salvarContaComValidacao(contaValidada, cliente);

        EmailMessage emailMessage = new EmailMessage(
                cliente.getLogin(),
                "Criação de Conta na Acabou o Mony",
                "Parabéns! Sua conta na Acabou o Mony foi criada com sucesso!!"
        );

        rabbitTemplate.convertAndSend("conta_exchange", "routing_emails", emailMessage);

        return contaSalva;
    }


    public Conta alterarConta(Long id, Conta contaAlterada){
        Conta conta = buscarContaPorId(id);
        conta.setAgencia(contaAlterada.getAgencia());
        conta.setTipoConta(contaAlterada.getTipoConta());

        if (contaRepository.existsByTipoContaAndClienteAndIdNot(conta.getTipoConta(), conta.getCliente(), conta.getId())){
            throw new ContaConflitoException("Esse Cliente já possui uma conta desse tipo.");
        }

        switch (conta.getTipoConta()) {
            case CONTA_CORRENTE -> {
                conta.setIsDebito(contaAlterada.getIsDebito());
                conta.setIsCredito(contaAlterada.getIsCredito());
                if (conta.getCliente() instanceof PessoaFisica pessoaFisica) {
                    conta.setLimiteCredito(pessoaFisica.getPerfilEconomico().limite);
                } else {
                    conta.setLimiteCredito(BigDecimal.valueOf(10_000.00));
                }
            }
            case CONTA_POUPANCA -> {
                conta.setIsDebito(true);
                conta.setIsCredito(false);
                conta.setLimiteCredito(BigDecimal.ZERO);
            }
            case CONTA_SALARIO -> {
                if (conta.getCliente() instanceof PessoaFisica) {
                    conta.setIsDebito(false);
                    conta.setIsCredito(false);
                    conta.setLimiteCredito(BigDecimal.ZERO);
                } else {
                    throw new ContaSalarioNaoPermitidaParaPJException("Pessoa Jurídica não pode abrir conta salário.");
                }
            }
        }
        return contaRepository.save(conta);
    }

    public Conta buscarContaPorId(Long id){
        return contaRepository.findById(id)
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada."));
    }

    public Conta alterarStatusConta(Long idConta){
        Conta conta = buscarContaPorId(idConta);
        conta.setIsAtiva(!conta.getIsAtiva());
        return contaRepository.save(conta);
    }

    public Conta atualizarSaldo(Long id, BigDecimal valor){
        Conta conta = buscarContaPorId(id);
        conta.atualizarSaldo(valor);
        return contaRepository.save(conta);
    }

    public void deletarConta(Long id){
        Conta conta = buscarContaPorId(id);
        contaRepository.delete(conta);
    }

    public String emailClientePorContaId(Long idConta){
        Conta conta = buscarContaPorId(idConta);
        Cliente cliente = clienteService.buscarClientePorId(conta.getCliente().getId());
        return cliente.getLogin();
    }

}
