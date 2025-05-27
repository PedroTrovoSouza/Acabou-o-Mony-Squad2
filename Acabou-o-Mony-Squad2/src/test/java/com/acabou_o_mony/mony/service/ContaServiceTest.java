package com.acabou_o_mony.mony.service;

import com.acabou_o_mony.mony.dto.conta.ContaRequestDto;
import com.acabou_o_mony.mony.entity.Conta;
import com.acabou_o_mony.mony.entity.PessoaFisica;
import com.acabou_o_mony.mony.entity.PessoaJuridica;
import com.acabou_o_mony.mony.enums.Genero;
import com.acabou_o_mony.mony.enums.PerfilEconomico;
import com.acabou_o_mony.mony.enums.TipoCliente;
import com.acabou_o_mony.mony.enums.TipoConta;
import com.acabou_o_mony.mony.exception.ContaSalarioNaoPermitidaParaPJException;
import com.acabou_o_mony.mony.repository.ContaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
                BigDecimal.valueOf(10_000.00), true, pessoaJuridica);
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
        assertEquals(1L, resultado1.getId());
        assertEquals(TipoConta.CONTA_CORRENTE, resultado1.getTipoConta());
        assertEquals(pessoaFisica, resultado1.getCliente());
    }

    @Test
    void deveCadastrarNovaContaDeClienteJuridicoComSucesso(){
        //Given
        ContaRequestDto contaRequestDto2 = new ContaRequestDto("001", TipoConta.CONTA_POUPANCA, true,
                false, pessoaJuridica.getId(), TipoCliente.PESSOA_JURIDICA);

        //When
        when(clienteService.buscarPessoaJuridicaPorId(anyLong())).thenReturn(pessoaJuridica);
        when(contaRepository.existsByTipoContaAndCliente(any(), any())).thenReturn(false);
        when(contaRepository.save(any())).thenReturn(contaDaPessoaJuridica);
        Conta resultado2 = contaService.abrirConta(contaRequestDto2);

        //Then
        assertEquals(contaDaPessoaJuridica, resultado2);
        assertEquals(2L, resultado2.getId());
        assertEquals(TipoConta.CONTA_POUPANCA, resultado2.getTipoConta());
        assertEquals(pessoaJuridica, resultado2.getCliente());
    }

    @Test
    void deveLancarExcessaoParaContaJuridicaDoTipoSalario(){
        //Given
        ContaRequestDto contaRequestDto2 = new ContaRequestDto("001", TipoConta.CONTA_SALARIO, true,
                false, pessoaJuridica.getId(), TipoCliente.PESSOA_JURIDICA);

        //When
        when(clienteService.buscarPessoaJuridicaPorId(anyLong())).thenReturn(pessoaJuridica);

        //Then
        assertThrows(ContaSalarioNaoPermitidaParaPJException.class, () -> contaService.abrirConta(contaRequestDto2));
    }

    @Test
    void deveAlterarTipoDeContaComSucesso(){
        //Given
        Conta contaParaAlteracao = new Conta("002", TipoConta.CONTA_SALARIO);

        //When
        when(contaRepository.existsByTipoContaAndClienteAndIdNot(any(), any(), anyLong())).thenReturn(false);
        when(contaRepository.findById(anyLong())).thenReturn(Optional.of(contaDaPessoaFisica));
        when(contaRepository.save(any())).thenReturn(contaDaPessoaFisica);

        Conta resultado = contaService.alterarConta(contaDaPessoaFisica.getId(), contaParaAlteracao);

        //Then
        assertEquals(TipoConta.CONTA_SALARIO, resultado.getTipoConta());
        assertEquals("002", resultado.getAgencia());
        assertFalse(resultado.getIsDebito());
        assertFalse(resultado.getIsCredito());
        assertEquals(BigDecimal.ZERO, resultado.getLimiteCredito());
        assertEquals(BigDecimal.valueOf(5_000.00), resultado.getSaldo());
    }

}