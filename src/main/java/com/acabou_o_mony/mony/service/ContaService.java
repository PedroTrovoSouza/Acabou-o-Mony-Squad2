package com.acabou_o_mony.mony.service;

import com.acabou_o_mony.mony.dto.conta.ContaRequestDto;
import com.acabou_o_mony.mony.entity.Cliente;
import com.acabou_o_mony.mony.entity.Conta;
import com.acabou_o_mony.mony.enums.TipoConta;
import com.acabou_o_mony.mony.exception.ContaConflitoException;
import com.acabou_o_mony.mony.exception.ContaNaoEncontradaException;
import com.acabou_o_mony.mony.mapper.ContaMapper;
import com.acabou_o_mony.mony.repository.ClienteRepository;
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

    public Conta cadastrarConta(ContaRequestDto dto){
        Cliente cliente = clienteService.buscarClientePorId(dto.idCliente());
        Conta conta = ContaMapper.toEntity(dto, cliente);

        if (contaRepository.existsByTipoContaAndCliente(conta.getTipoConta(), cliente)){
            throw new ContaConflitoException("Esse Cliente já possui uma conta desse tipo.");
        }

        if (conta.getTipoConta() == TipoConta.CONTA_CORRENTE){
            conta.setLimiteCredito(cliente.getPerfilEconomico().limite);
        }else if(conta.getTipoConta() == TipoConta.CONTA_POUPANCA){
            conta.setIsDebito(true);
            conta.setIsCredito(false);
            conta.setLimiteCredito(BigDecimal.ZERO);
        } else if (conta.getTipoConta() == TipoConta.CONTA_SALARIO) {
            conta.setIsDebito(false);
            conta.setIsCredito(false);
            conta.setLimiteCredito(BigDecimal.ZERO);
        }
        conta.setIsAtiva(true);
        conta.setCliente(cliente);
        conta.setSaldo(BigDecimal.ZERO);
        return contaRepository.save(conta);
    }

    public Conta alterarConta(Long id, Conta contaAlterada){
            Conta conta = buscarContaPorId(id);
            conta.setAgencia(contaAlterada.getAgencia());
            conta.setTipoConta(contaAlterada.getTipoConta());

            if (conta.getTipoConta() == TipoConta.CONTA_CORRENTE){
                conta.setIsDebito(contaAlterada.getIsDebito());
                conta.setIsCredito(contaAlterada.getIsCredito());
                conta.setLimiteCredito(conta.getCliente().getPerfilEconomico().limite);
            }else if(conta.getTipoConta() == TipoConta.CONTA_POUPANCA){
                conta.setIsDebito(true);
                conta.setIsCredito(false);
                conta.setLimiteCredito(BigDecimal.ZERO);
            } else if (conta.getTipoConta() == TipoConta.CONTA_SALARIO) {
                conta.setIsDebito(false);
                conta.setIsCredito(false);
                conta.setLimiteCredito(BigDecimal.ZERO);
            }
            return contaRepository.save(conta);
    }

    public Conta buscarContaPorId(Long id){
        if (contaRepository.existsById(id)){
            return contaRepository.getReferenceById(id);
        }
        throw new ContaNaoEncontradaException("Conta não encontrada.");
    }

    public Conta alterarStatusConta(Long idConta){
        Conta conta = buscarContaPorId(idConta);
        if (contaRepository.existsByTipoContaAndCliente(conta.getTipoConta(), conta.getCliente())){
            conta.setIsAtiva(!conta.getIsAtiva());
            return conta;
        }
        throw new ContaNaoEncontradaException("Conta não encontrada.");
    }

    public void deletarConta(Long id){
        Conta conta = buscarContaPorId(id);
        contaRepository.delete(conta);
    }

}
