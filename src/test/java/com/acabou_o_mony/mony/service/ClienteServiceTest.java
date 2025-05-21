package com.acabou_o_mony.mony.service;

import com.acabou_o_mony.mony.entity.Cliente;
import com.acabou_o_mony.mony.entity.PessoaFisica;
import com.acabou_o_mony.mony.entity.PessoaJuridica;
import com.acabou_o_mony.mony.enums.Genero;
import com.acabou_o_mony.mony.enums.PerfilEconomico;
import com.acabou_o_mony.mony.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;
    @InjectMocks
    private ClienteService clienteService;

    private PessoaFisica pessoaFisica;

    private PessoaJuridica pessoaJuridica;

    @BeforeEach
    void setUp(){
        pessoaFisica = new PessoaFisica(1L, "Fernando", "12345678901", PerfilEconomico.MEDIO, LocalDate.of(2005,10,02),
                Genero.MASCULINO);
    }

    @Test
    void deveCadastrarNovoClienteComSucesso(){
        //Given


        //When

        //Then
    }
}