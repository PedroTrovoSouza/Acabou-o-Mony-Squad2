package com.acabou_o_mony.mony.service;

import com.acabou_o_mony.mony.dto.conta.ContaRequestDto;
import com.acabou_o_mony.mony.entity.Conta;
import com.acabou_o_mony.mony.entity.PessoaFisica;
import com.acabou_o_mony.mony.entity.PessoaJuridica;
import com.acabou_o_mony.mony.enums.Genero;
import com.acabou_o_mony.mony.enums.PerfilEconomico;
import com.acabou_o_mony.mony.enums.TipoCliente;
import com.acabou_o_mony.mony.enums.TipoConta;
import com.acabou_o_mony.mony.repository.ContaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ContaServiceTest {

    @Mock
    private ContaRepository contaRepository;
    @Mock
    private ClienteService clienteService;
    @InjectMocks
    private ContaService contaService;

    private Conta contaDaPessoaFisica;

    private Conta contaDaPessoaJuridica;

    private PessoaFisica pessoaFisica;

    private PessoaJuridica pessoaJuridica;


    @BeforeEach
    void setUp(){
        pessoaFisica = new PessoaFisica(1L, "Fernando", "12345678901", PerfilEconomico.MEDIO, LocalDate.of(2005,10,02),
                Genero.MASCULINO);
        contaDaPessoaFisica = new Conta(1L, "001", TipoConta.CONTA_CORRENTE,  BigDecimal.valueOf(5_000.00), true, true,
                BigDecimal.valueOf(6_000.00), true, pessoaFisica);

        pessoaJuridica = new PessoaJuridica(1L, "Solutis", "09876543212", LocalDate.of(2005,10,02));
        contaDaPessoaJuridica = new Conta(2L, "001", TipoConta.CONTA_POUPANCA,  BigDecimal.valueOf(100_000.00), true, true,
                BigDecimal.valueOf(10_000.00), true, pessoaFisica);
    }

    @Test
    void deveCadastrarNovaContaDeClienteFisicoComSucesso(){
        //Given
        ContaRequestDto contaRequestDto1 = new ContaRequestDto("001", TipoConta.CONTA_CORRENTE, true,
                true, pessoaFisica.getId(), TipoCliente.PESSOA_FISICA);

        //When
        when(clienteService.buscarPessoaFisicaPorId(anyLong())).thenReturn(pessoaFisica);
        when(contaRepository.existsByTipoContaAndCliente(any(), any())).thenReturn(false);
        when(contaRepository.save(any())).thenReturn(contaDaPessoaFisica);
        Conta resultado1 = contaService.abrirConta(contaRequestDto1);

        //Then
        assertEquals(contaDaPessoaFisica, resultado1);
    }

    @Test
    void deveCadastrarNovaContaDeClienteJuridicoComSucesso(){
        //Given
        ContaRequestDto contaRequestDto2 = new ContaRequestDto("001", TipoConta.CONTA_POUPANCA, true,
                false, pessoaJuridica.getId(), TipoCliente.PESSOA_FISICA);

        //When
        when(clienteService.buscarPessoaJuridicaPorId(anyLong())).thenReturn(pessoaJuridica);
        when(contaRepository.existsByTipoContaAndCliente(any(), any())).thenReturn(false);
        when(contaRepository.save(any())).thenReturn(contaDaPessoaJuridica);
        Conta resultado2 = contaService.abrirConta(contaRequestDto2);

        //Then
        assertEquals(contaDaPessoaJuridica, resultado2);
    }

}