package contas.service.contas_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import contas.service.contas_service.dto.cliente.FisicaAtualizacaoDto;
import contas.service.contas_service.dto.cliente.FisicaResponseDto;
import contas.service.contas_service.dto.cliente.JuridicaResponseDto;
import contas.service.contas_service.entity.PessoaFisica;
import contas.service.contas_service.entity.PessoaJuridica;
import contas.service.contas_service.enums.Genero;
import contas.service.contas_service.enums.PerfilEconomico;
import contas.service.contas_service.mapper.ClienteMapper;
import contas.service.contas_service.service.ClienteService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    private PessoaFisica pessoaFisica;

    private PessoaJuridica pessoaJuridica;

    @BeforeEach
    void setUp(){
        pessoaFisica = new PessoaFisica("Fernando", "fernando@gmail.com", "senha", PerfilEconomico.MEDIO, "12345678901",  LocalDate.of(2005,10,02),
                Genero.MASCULINO);
        pessoaJuridica = new PessoaJuridica("Solutis","solutis@gmail.com", "senha", PerfilEconomico.EMPRESA,"09876543212", LocalDate.of(2005,10,02));
    }

    @Test
    @DisplayName("Deve listar pessoas fisicas cadastradas e codigo 200")
    void deveRetornarCodigo200EListarPessoasFisicas() throws Exception {
        clienteService.cadastrarPessoaFisica(pessoaFisica);

        mockMvc.perform(get("/clientes/pf"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cpf").value(pessoaFisica.getCpf()));
    }

    @Test
    @DisplayName("Deve listar pessoas jurídicas cadastradas e codigo 200")
    void deveRetornarCodigo200EListarPessoasJuridicas() throws Exception {
        clienteService.cadastrarPessoaJuridica(pessoaJuridica);

        mockMvc.perform(get("/clientes/pj"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cnpj").value(pessoaJuridica.getCnpj()));
    }

    @Test
    @DisplayName("Deve listar lsta vazia de pessoas fisicas e codigo 204")
    void deveRetornarListaVaziaFisicas() throws Exception {
        mockMvc.perform(get("/clientes/pj"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    @DisplayName("Deve listar lsta vazia de pessoas juridicas e codigo 204")
    void deveRetornarListaVaziaJuridicas() throws Exception {
        mockMvc.perform(get("/clientes/pf"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    @DisplayName("Deve cadastrar pessoa Fisica com sucesso")
    void deveCadastrarPessoaFisicaComSucesso() throws Exception {
        FisicaResponseDto responseDto = ClienteMapper.toResponseDto(pessoaFisica);

        mockMvc.perform(post("/clientes/pf").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(responseDto)))
                .andExpect(jsonPath("$[0].cpf").value(pessoaFisica.getCpf()))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve cadastrar pessoa Juridica com sucesso")
    void deveCadastrarPessoaJuridicaComSucesso() throws Exception {
        JuridicaResponseDto responseDto = ClienteMapper.toResponseDto(pessoaJuridica);

        mockMvc.perform(post("/clientes/pj").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(responseDto)))
                .andExpect(jsonPath("$[0].cnpj").value(pessoaJuridica.getCnpj()))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve retornar a Pessoa Fisica com Id informado")
    void deveRetornarPessoaFisicaComIdInformado() throws Exception {
        PessoaFisica saved = clienteService.cadastrarPessoaFisica(pessoaFisica);

        mockMvc.perform(get("/clientes/pf/{id}",pessoaFisica.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(saved.getEmail()));
    }

    @Test
    @DisplayName("Deve retornar a Pessoa Juridica com Id informado")
    void deveRetornarPessoaJuridicaComIdInformado() throws Exception {
        PessoaJuridica saved = clienteService.cadastrarPessoaJuridica(pessoaJuridica);

        mockMvc.perform(get("/clientes/pj/{id}",pessoaJuridica.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(saved.getEmail()));
    }

    @Test
    @DisplayName("Deve atualizar a Pessoa Fisica com sucesso")
    void deveAtualizarPessoaFisicaComSucesso() throws Exception {
        FisicaAtualizacaoDto atualizacaoDto = new FisicaAtualizacaoDto
                ("Fernando", "bomprogramador@gmail.com", LocalDate.of(2005,10,02),
                        Genero.MASCULINO);
        clienteService.cadastrarPessoaFisica(pessoaFisica);

        mockMvc.perform(put("/clientes/pf/{id}", pessoaFisica.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atualizacaoDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(atualizacaoDto.email()));
    }
}