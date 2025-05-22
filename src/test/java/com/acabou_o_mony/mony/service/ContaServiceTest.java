package com.acabou_o_mony.mony.service;

import com.acabou_o_mony.mony.repository.ContaRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ContaServiceTest {

    @Mock
    private ContaRepository contaRepository;
    @Mock
    private ClienteService clienteService;
    @InjectMocks
    private ContaService contaService;
}