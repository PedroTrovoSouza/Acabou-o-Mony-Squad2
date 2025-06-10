package com.example.transacao_service.service;

import com.example.transacao_service.dto.transacao.TransacaoRequestDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransacaoServiceValidationTests {

	@Test
	void deveValidarTransacaoComCartaoIdValido() {
		TransacaoService service = new TransacaoService(null, null, null);

		TransacaoRequestDTO dto = new TransacaoRequestDTO();
		dto.setCartaoId(1L); // cartao válido

		assertDoesNotThrow(() -> service.validarTransacao(dto));
	}

	@Test
	void deveLancarExcecaoQuandoTransacaoForNula() {
		TransacaoService service = new TransacaoService(null, null, null);

		RuntimeException exception = assertThrows(RuntimeException.class,
				() -> service.validarTransacao(null));

		assertEquals("Transação ou Cartão inválido.", exception.getMessage());
	}

	@Test
	void deveLancarExcecaoQuandoCartaoIdForNulo() {
		TransacaoService service = new TransacaoService(null, null, null);

		TransacaoRequestDTO dto = new TransacaoRequestDTO();
		dto.setCartaoId(null); // cartao inválido

		RuntimeException exception = assertThrows(RuntimeException.class,
				() -> service.validarTransacao(dto));

		assertEquals("Transação ou Cartão inválido.", exception.getMessage());
	}
}
