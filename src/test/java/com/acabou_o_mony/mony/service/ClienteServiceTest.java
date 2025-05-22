package com.acabou_o_mony.mony.service;

import com.acabou_o_mony.mony.entity.PessoaFisica;
import com.acabou_o_mony.mony.entity.PessoaJuridica;
import com.acabou_o_mony.mony.enums.Genero;
import com.acabou_o_mony.mony.enums.PerfilEconomico;
import com.acabou_o_mony.mony.exception.ClienteConflitoException;
import com.acabou_o_mony.mony.repository.PessoaFisicaRepository;
import com.acabou_o_mony.mony.repository.PessoaJuridicaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private PessoaFisicaRepository fisicaRepository;
    @Mock
    private PessoaJuridicaRepository juridicaRepository;
    @InjectMocks
    private ClienteService clienteService;

    private PessoaFisica pessoaFisica;

    private PessoaJuridica pessoaJuridica;

    @BeforeEach
    void setUp(){
        pessoaFisica = new PessoaFisica(1L, "Fernando", "12345678901", PerfilEconomico.MEDIO, LocalDate.of(2005,10,02),
                Genero.MASCULINO);
        pessoaJuridica = new PessoaJuridica(1L, "Solutis", "09876543212", LocalDate.of(2005,10,02));
    }

    @Test
    void deveCadastrarNovoClienteComSucesso(){
        //Given

        //When
        when(fisicaRepository.existsByCpf(anyString())).thenReturn(false);
        when(juridicaRepository.existsByCnpj(anyString())).thenReturn(false);
        when(fisicaRepository.save(any())).thenReturn(pessoaFisica);
        when(juridicaRepository.save(any())).thenReturn(pessoaJuridica);

        //Then
        PessoaFisica pessoaFisicaSalva = clienteService.cadastrarPessoaFisica(pessoaFisica);
        PessoaJuridica pessoaJuridicaSalva = clienteService.cadastrarPessoaJuridica(pessoaJuridica);

        //Assert
        assertEquals(pessoaFisica, pessoaFisicaSalva);
        assertEquals(pessoaJuridica, pessoaJuridicaSalva);
    }

    @Test
    void deveLancarExcessaoAoTentarCadastrarClienteComCpfOuCnpjCadastrado(){
        //Given

        //When
        when(fisicaRepository.existsByCpf(anyString())).thenReturn(true);
        when(juridicaRepository.existsByCnpj(anyString())).thenReturn(true);

        //Then

        //Assert
        assertThrows(ClienteConflitoException.class, () -> clienteService.cadastrarPessoaFisica(pessoaFisica));
        assertThrows(ClienteConflitoException.class, () -> clienteService.cadastrarPessoaJuridica(pessoaJuridica));
    }
}