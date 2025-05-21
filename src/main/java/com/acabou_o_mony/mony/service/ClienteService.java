package com.acabou_o_mony.mony.service;

import com.acabou_o_mony.mony.entity.Cliente;
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

        }
        return null;
    }
}
