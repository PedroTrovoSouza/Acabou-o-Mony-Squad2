package contas.service.contas_service.controller;

import contas.service.contas_service.dto.cartao.CartaoRequestDTO;
import contas.service.contas_service.dto.cartao.CartaoResponseDTO;
import contas.service.contas_service.service.CartaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cartao")
public class CartaoController {

    @Autowired
    private CartaoService service;

    @PostMapping
    public ResponseEntity<CartaoResponseDTO> salvar(@RequestBody CartaoRequestDTO dto) {
        CartaoResponseDTO cartaoResponse = service.salvarCartao(dto);

        if (cartaoResponse == null) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.status(201).body(cartaoResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartaoResponseDTO> listarPorId(@PathVariable Long id) {
        CartaoResponseDTO cartaoResponseDTO = service.listarCartaoPorId(id);
        if (cartaoResponseDTO == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(cartaoResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartaoResponseDTO> atualizarCartao(@PathVariable Long id, @RequestBody @Valid CartaoRequestDTO dto) {
        CartaoResponseDTO atualizado = service.atualizarCartao(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        boolean deletou = service.deletarPorId(id);
        if(!deletou) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(204).build();
    }
}
