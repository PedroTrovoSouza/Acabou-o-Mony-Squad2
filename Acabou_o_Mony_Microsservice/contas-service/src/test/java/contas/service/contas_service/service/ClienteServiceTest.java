package contas.service.contas_service.service;


import contas.service.contas_service.entity.PessoaFisica;
import contas.service.contas_service.entity.PessoaJuridica;
import contas.service.contas_service.enums.Genero;
import contas.service.contas_service.enums.PerfilEconomico;
import contas.service.contas_service.exception.ClienteConflitoException;
import contas.service.contas_service.repository.PessoaFisicaRepository;
import contas.service.contas_service.repository.PessoaJuridicaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        pessoaFisica = new PessoaFisica(1L, "Fernando", "fernando@gmail.com", "12345678901", PerfilEconomico.MEDIO, LocalDate.of(2005,10,02),
                Genero.MASCULINO);
        pessoaJuridica = new PessoaJuridica(1L, "Solutis","solutis@gmail.com", "09876543212", LocalDate.of(2005,10,02));
    }

    @Test
    void deveCadastrarNovoClientesFisicoEJuridicoComSucesso(){
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