package com.acabou_o_mony.mony.service;

import com.acabou_o_mony.mony.dto.conta.ContaRequestDto;
import com.acabou_o_mony.mony.entity.Cliente;
import com.acabou_o_mony.mony.entity.Conta;
import com.acabou_o_mony.mony.entity.PessoaFisica;
import com.acabou_o_mony.mony.enums.TipoCliente;
import com.acabou_o_mony.mony.enums.TipoConta;
import com.acabou_o_mony.mony.exception.ContaConflitoException;
import com.acabou_o_mony.mony.exception.ContaNaoEncontradaException;
import com.acabou_o_mony.mony.exception.ContaSalarioNaoPermitidaParaPJException;
import com.acabou_o_mony.mony.exception.TipoClienteInvalidoException;
import com.acabou_o_mony.mony.mapper.ContaMapper;
import com.acabou_o_mony.mony.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContaService {
    private final ContaRepository contaRepository;
    private final ClienteService clienteService;

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
            if (cliente instanceof PessoaFisica pessoaFisica){
                conta.setLimiteCredito(pessoaFisica.getPerfilEconomico().limite);
            }else {
                conta.setLimiteCredito(BigDecimal.valueOf(10_000.00));
            }
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

    public Conta abrirConta(ContaRequestDto dto){
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

        return salvarContaComValidacao(contaValidada, cliente);
    }

    public Conta alterarConta(Long id, Conta contaAlterada){
            Conta conta = buscarContaPorId(id);
            conta.setAgencia(contaAlterada.getAgencia());
            conta.setTipoConta(contaAlterada.getTipoConta());

            if (contaRepository.existsByTipoContaAndCliente(conta.getTipoConta(), conta.getCliente())){
                throw new ContaConflitoException("Esse Cliente já possui uma conta desse tipo.");
            }

            if (conta.getTipoConta() == TipoConta.CONTA_CORRENTE){
                conta.setIsDebito(contaAlterada.getIsDebito());
                conta.setIsCredito(contaAlterada.getIsCredito());
                if (conta.getCliente() instanceof PessoaFisica pessoaFisica){
                    conta.setLimiteCredito(pessoaFisica.getPerfilEconomico().limite);
                }else {
                    conta.setLimiteCredito(BigDecimal.valueOf(10_000.00));
                }
            }else if(conta.getTipoConta() == TipoConta.CONTA_POUPANCA){
                conta.setIsDebito(true);
                conta.setIsCredito(false);
                conta.setLimiteCredito(BigDecimal.ZERO);
            } else if (conta.getTipoConta() == TipoConta.CONTA_SALARIO) {
                if (conta.getCliente() instanceof PessoaFisica){
                    conta.setIsDebito(false);
                    conta.setIsCredito(false);
                    conta.setLimiteCredito(BigDecimal.ZERO);
                }else {
                    throw new ContaSalarioNaoPermitidaParaPJException("Pessoa Jurídica não pode abrir conta salário.");
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

    public void deletarConta(Long id){
        Conta conta = buscarContaPorId(id);
        contaRepository.delete(conta);
    }

}
