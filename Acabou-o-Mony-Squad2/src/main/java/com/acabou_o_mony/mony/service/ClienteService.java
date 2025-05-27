package com.acabou_o_mony.mony.service;

import com.acabou_o_mony.mony.entity.PessoaFisica;
import com.acabou_o_mony.mony.entity.PessoaJuridica;
import com.acabou_o_mony.mony.exception.ClienteConflitoException;
import com.acabou_o_mony.mony.exception.ClienteNaoEncontradoException;
import com.acabou_o_mony.mony.repository.PessoaFisicaRepository;
import com.acabou_o_mony.mony.repository.PessoaJuridicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final PessoaJuridicaRepository juridicaRepository;
    private final PessoaFisicaRepository fisicaRepository;

    public List<PessoaFisica> listarPessoaFisica(){
        return fisicaRepository.findAll();
    }

    public List<PessoaJuridica> listarPessoaJuridica(){
        return juridicaRepository.findAll();
    }

    public PessoaFisica buscarPessoaFisicaPorId(Long id){
        return fisicaRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Pessoa Fisica não enconntrada"));
    }

    public PessoaJuridica buscarPessoaJuridicaPorId(Long id){
        return juridicaRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Pessoa Fisica não enconntrada"));
    }

    public PessoaFisica cadastrarPessoaFisica(PessoaFisica fisica){
        if (fisicaRepository.existsByCpf(fisica.getCpf())){
            throw new ClienteConflitoException("CPF já cadastrado.");
        }
        return fisicaRepository.save(fisica);
    }

    public PessoaJuridica cadastrarPessoaJuridica(PessoaJuridica juridica){
        if (juridicaRepository.existsByCnpj(juridica.getCnpj())){
            throw new ClienteConflitoException("CNPJ já cadastrado.");
        }
        return juridicaRepository.save(juridica);
    }

    public PessoaFisica atualizarPessoaFisica(Long id, PessoaFisica fisica){
        if (fisicaRepository.existsById(id)){
            PessoaFisica pessoaExistente = fisicaRepository.getReferenceById(id);
            pessoaExistente.setDataNascimento(fisica.getDataNascimento());
            pessoaExistente.setGenero(fisica.getGenero());
            return fisicaRepository.save(pessoaExistente);
        }
        throw new ClienteNaoEncontradoException("Pessoa Física não encontrado;");
    }

    public PessoaFisica buscarPessoaPorCpf(String cpf){
        if (fisicaRepository.existsByCpf(cpf)){
            return fisicaRepository.getPessoaFisicaByCpf(cpf);
        }
        throw new ClienteNaoEncontradoException("Cliente não encontrado;");
    }
    public PessoaJuridica buscarEmpresaPorCnpj(String cnpj){
        if (juridicaRepository.existsByCnpj(cnpj)){
            return juridicaRepository.getPessoaJuridicaByCnpj(cnpj);
        }
        throw new ClienteNaoEncontradoException("Cliente não encontrado;");
    }

    public void deletarPessoaPorCpf(String cpf){
      PessoaFisica cliente = buscarPessoaPorCpf(cpf);
      fisicaRepository.deleteById(cliente.getId());
    }
    public void deletarEmpresaPorCnpj(String cnpj){
        PessoaJuridica cliente = buscarEmpresaPorCnpj(cnpj);
        juridicaRepository.deleteById(cliente.getId());
    }
}
