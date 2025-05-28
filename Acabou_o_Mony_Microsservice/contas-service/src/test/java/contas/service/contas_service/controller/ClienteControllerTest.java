package contas.service.contas_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import contas.service.contas_service.service.ClienteService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

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



    @Test
    @DisplayName("Deve listar pessoas fisicas cadastradas e codigo 200")
    void deveRetornarCodigo200EListarPessoasFisicas(){

    }


}