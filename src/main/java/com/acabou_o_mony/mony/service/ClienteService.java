package com.acabou_o_mony.mony.service;

import com.acabou_o_mony.mony.entity.Cliente;
import com.acabou_o_mony.mony.exception.ClienteConflitoException;
import com.acabou_o_mony.mony.exception.ClienteNaoEncontradoException;
import com.acabou_o_mony.mony.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public List<Cliente> listarClientes(){
        return clienteRepository.findAll();
    }

    public Cliente cadastrarCliente(Cliente cliente){
        if (clienteRepository.existsByCpfOuCnpj(cliente.getCpfOuCnpj())){
            throw new ClienteConflitoException("CPF ou CNPJ já cadastrado.");
        }
        return clienteRepository.save(cliente);
    }

    public Cliente atualizarCliente(Long id, Cliente cliente){
        if (clienteRepository.existsById(id)){
            Cliente clienteExistente = clienteRepository.getReferenceById(id);
            clienteExistente.setDataNascimento(cliente.getDataNascimento());
            clienteExistente.setGenero(cliente.getGenero());
            return clienteRepository.save(clienteExistente);
        }
        throw new ClienteNaoEncontradoException("Cliente não encontrado;");
    }

    public Cliente buscarClientePorId(Long id){
        if (clienteRepository.existsById(id)){
            return clienteRepository.getReferenceById(id);
        }
        throw new ClienteNaoEncontradoException("Cliente não encontrado;");
    }

    public void deletarCliente(Long id){
      Cliente cliente = buscarClientePorId(id);
      clienteRepository.deleteById(id);
    }

}
