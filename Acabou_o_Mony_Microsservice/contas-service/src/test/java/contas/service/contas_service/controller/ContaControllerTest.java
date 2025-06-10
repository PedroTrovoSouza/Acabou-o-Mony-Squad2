package contas.service.contas_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import contas.service.contas_service.dto.conta.ContaAtualizacaoDto;
import contas.service.contas_service.dto.conta.ContaRequestDto;
import contas.service.contas_service.entity.Conta;
import contas.service.contas_service.entity.PessoaFisica;
import contas.service.contas_service.entity.PessoaJuridica;
import contas.service.contas_service.enums.Genero;
import contas.service.contas_service.enums.PerfilEconomico;
import contas.service.contas_service.enums.TipoCliente;
import contas.service.contas_service.enums.TipoConta;
import contas.service.contas_service.service.ClienteService;
import contas.service.contas_service.service.ContaService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ContaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContaService contaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    private PessoaFisica pessoaFisica;

    private PessoaJuridica pessoaJuridica;

    private Conta contaPessoaJuridica;

    private Conta contaPessoaFisica;


    @BeforeEach
    void setUp(){
        pessoaFisica = new PessoaFisica("Fernando", "fernando@gmail.com", "senha", PerfilEconomico.MEDIO,"12345678901",  LocalDate.of(2005,10,02),
                Genero.MASCULINO);
        pessoaJuridica = new PessoaJuridica("Solutis","solutis@gmail.com", "senha", "09876543212", LocalDate.of(2005,10,02));
        contaPessoaJuridica = new Conta("001", TipoConta.CONTA_CORRENTE, BigDecimal.valueOf(10_000.00), true, true, BigDecimal.valueOf(10_000.0), true, pessoaJuridica);
        contaPessoaFisica = new Conta("002", TipoConta.CONTA_POUPANCA, BigDecimal.valueOf(3_000.00), true, false, BigDecimal.valueOf(5_000.00), true, pessoaFisica);
    }

    @Test
    @DisplayName("Deve listar todas as contas e retornar codigo 200")
    void deveRetornarCodigo200EListarContas() throws Exception {
        clienteService.cadastrarPessoaJuridica(pessoaJuridica);
        ContaRequestDto requestDto1 = new ContaRequestDto(contaPessoaJuridica.getAgencia(), contaPessoaJuridica.getTipoConta(),
                contaPessoaJuridica.getIsDebito(), contaPessoaJuridica.getIsCredito(), pessoaJuridica.getId(), TipoCliente.PESSOA_JURIDICA);

        clienteService.cadastrarPessoaFisica(pessoaFisica);
        ContaRequestDto requestDto2 = new ContaRequestDto(contaPessoaFisica.getAgencia(), contaPessoaFisica.getTipoConta(),
                contaPessoaFisica.getIsDebito(), contaPessoaFisica.getIsCredito(), pessoaFisica.getId(), TipoCliente.PESSOA_FISICA);

        contaService.abrirConta(requestDto1);
        contaService.abrirConta(requestDto2);

        mockMvc.perform(get("/contas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].agencia").value(contaPessoaJuridica.getAgencia()))
                .andExpect(jsonPath("$[1].agencia").value(contaPessoaFisica.getAgencia()));
    }

    @Test
    @DisplayName("Deve listar lista vazia e retornar codigo 204")
    void deveRetornarCodigo204EListaVazia() throws Exception {
        mockMvc.perform(get("/contas"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    @DisplayName("Deve abrir uma nova conta para pesssoa Juridica com sucesso")
    void deveAbrirContaParaPessoaJuridicaComSucesso() throws Exception {
        clienteService.cadastrarPessoaJuridica(pessoaJuridica);
        ContaRequestDto requestDto = new ContaRequestDto(contaPessoaJuridica.getAgencia(), contaPessoaJuridica.getTipoConta(),
                contaPessoaJuridica.getIsDebito(), contaPessoaJuridica.getIsCredito(), pessoaJuridica.getId(), TipoCliente.PESSOA_JURIDICA);

        mockMvc.perform(post("/contas").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipoConta").value(TipoConta.CONTA_CORRENTE.name()));
    }

    @Test
    @DisplayName("Deve abrir uma nova conta para pesssoa Juridica com sucesso e retornar a pessoa cadastrada")
    void deveAbrirContaParaPessoaFisicaComSucesso() throws Exception {
        clienteService.cadastrarPessoaFisica(pessoaFisica);
        ContaRequestDto requestDto = new ContaRequestDto(contaPessoaFisica.getAgencia(), contaPessoaFisica.getTipoConta(),
                contaPessoaFisica.getIsDebito(), contaPessoaFisica.getIsCredito(), pessoaFisica.getId(), TipoCliente.PESSOA_FISICA);

        mockMvc.perform(post("/contas").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipoConta").value(TipoConta.CONTA_POUPANCA.name()));
    }

    @Test
    @DisplayName("Deve desativar conta com sucesso e retornar OK")
    void deveDesativarUmaContaComSucesso() throws Exception {
        clienteService.cadastrarPessoaJuridica(pessoaJuridica);
        ContaRequestDto requestDto1 = new ContaRequestDto(contaPessoaJuridica.getAgencia(), contaPessoaJuridica.getTipoConta(),
                contaPessoaJuridica.getIsDebito(), contaPessoaJuridica.getIsCredito(), pessoaJuridica.getId(), TipoCliente.PESSOA_JURIDICA);

        Conta contaAberta = contaService.abrirConta(requestDto1);

        mockMvc.perform(put("/contas/status/{id}", contaAberta.getId()))
                .andExpect(jsonPath("$.isAtiva").value(false));
    }

    @Test
    @DisplayName("Deve somar saldo do cliente com valor passado")
    void deveSomarSaldoDoClienteComValorPassado() throws Exception {
        clienteService.cadastrarPessoaJuridica(pessoaJuridica);
        ContaRequestDto requestDto1 = new ContaRequestDto(contaPessoaJuridica.getAgencia(), contaPessoaJuridica.getTipoConta(),
                contaPessoaJuridica.getIsDebito(), contaPessoaJuridica.getIsCredito(), pessoaJuridica.getId(), TipoCliente.PESSOA_JURIDICA);

        Conta contaAberta = contaService.abrirConta(requestDto1);

        mockMvc.perform(put("/contas/saldo/{id}", contaAberta.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(2_000.0)))
                .andExpect(jsonPath("$.saldo").value(2_000.0));
    }

    @Test
    @DisplayName("Deve subtrair saldo do cliente com valor passado")
    void deveSubtrairSaldoDoClienteComValorPassado() throws Exception {
        clienteService.cadastrarPessoaJuridica(pessoaJuridica);
        ContaRequestDto requestDto1 = new ContaRequestDto(contaPessoaJuridica.getAgencia(), contaPessoaJuridica.getTipoConta(),
                contaPessoaJuridica.getIsDebito(), contaPessoaJuridica.getIsCredito(), pessoaJuridica.getId(), TipoCliente.PESSOA_JURIDICA);

        Conta contaAberta = contaService.abrirConta(requestDto1);

        mockMvc.perform(put("/contas/saldo/{id}", contaAberta.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(-1_000.0)))
                .andExpect(jsonPath("$.saldo").value(-1_000.0));
    }

    @Test
    @DisplayName("Deve alterar CONTA_CORRENTE para CONTA_SALARIo")
    void deveAlterarTipoDeConta() throws Exception {
        clienteService.cadastrarPessoaFisica(pessoaFisica);
        ContaRequestDto requestDto = new ContaRequestDto(contaPessoaFisica.getAgencia(), contaPessoaFisica.getTipoConta(),
                contaPessoaFisica.getIsDebito(), contaPessoaFisica.getIsCredito(), pessoaFisica.getId(), TipoCliente.PESSOA_FISICA);
        ContaAtualizacaoDto atualizacaoDto = new ContaAtualizacaoDto("003", TipoConta.CONTA_SALARIO);

        Conta contaAberta = contaService.abrirConta(requestDto);

        mockMvc.perform(put("/contas/conta/{id}", contaAberta.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atualizacaoDto)))
                .andExpect(jsonPath("$.agencia").value(atualizacaoDto.agencia()))
                .andExpect(jsonPath("$.tipoConta").value(atualizacaoDto.tipoConta().name()));
    }

}