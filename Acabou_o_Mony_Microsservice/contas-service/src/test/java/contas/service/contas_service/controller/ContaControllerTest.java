package contas.service.contas_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        pessoaFisica = new PessoaFisica("Fernando", "fernando@gmail.com", "12345678901", PerfilEconomico.MEDIO, LocalDate.of(2005,10,02),
                Genero.MASCULINO);
        pessoaJuridica = new PessoaJuridica("Solutis","solutis@gmail.com", "09876543212", LocalDate.of(2005,10,02));
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
                contaPessoaFisica.getIsDebito(), contaPessoaFisica.getIsCredito(), contaPessoaFisica.getId(), TipoCliente.PESSOA_FISICA);

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
    @DisplayName("Deve abrir uma nova conta para pesssoa física com sucesso")
    void deveAbrirContaParaPessoaFisicaComSucesso() throws Exception {
        clienteService.cadastrarPessoaFisica(pessoaFisica);
        ContaRequestDto requestDto = new ContaRequestDto(contaPessoaJuridica.getAgencia(), contaPessoaJuridica.getTipoConta(),
                contaPessoaJuridica.getIsDebito(), contaPessoaJuridica.getIsCredito(), pessoaJuridica.getId(), TipoCliente.PESSOA_JURIDICA);

        mockMvc.perform(post("/contas").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].tipoConta").value(TipoConta.CONTA_POUPANCA))
                .andExpect(jsonPath("$[0].tipoCliente").value(TipoCliente.PESSOA_JURIDICA));
    }

}