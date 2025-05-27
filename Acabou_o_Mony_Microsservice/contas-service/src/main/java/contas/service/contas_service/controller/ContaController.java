package contas.service.contas_service.controller;


import contas.service.contas_service.dto.conta.ContaAtualizacaoDto;
import contas.service.contas_service.dto.conta.ContaRequestDto;
import contas.service.contas_service.dto.conta.ContaResponseDto;
import contas.service.contas_service.entity.Conta;
import contas.service.contas_service.mapper.ContaMapper;
import contas.service.contas_service.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/contas")
@RequiredArgsConstructor
public class ContaController {
    private final ContaService contaService;

    @GetMapping
    public ResponseEntity<List<ContaResponseDto>> listarTodasContas(){
        List<Conta> contas = contaService.listarContas();
        List<ContaResponseDto> response = contas.stream().map(ContaMapper::toResponse).toList();
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ContaResponseDto> cadastrarConta(@RequestBody ContaRequestDto contaParaCadastrar){
        Conta conta = contaService.abrirConta(contaParaCadastrar);
        ContaResponseDto response = ContaMapper.toResponse(conta);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaResponseDto> alterarConta (@PathVariable Long idCliente, @RequestBody ContaAtualizacaoDto contaParaAtualizar){
        Conta conta = ContaMapper.toEntity(contaParaAtualizar);
        Conta contaAtualizada = contaService.alterarConta(idCliente, conta);
        ContaResponseDto response = ContaMapper.toResponse(contaAtualizada);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ContaResponseDto> ativarOuDesativarConta(@PathVariable Long id){
        Conta conta = contaService.alterarStatusConta(id);
        ContaResponseDto response = ContaMapper.toResponse(conta);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaResponseDto> atualizarSaldoDoCliente(@PathVariable Long id, @RequestBody Double valor){
        Conta conta = contaService.atualizarSaldo(id, BigDecimal.valueOf(valor));
        ContaResponseDto response = ContaMapper.toResponse(conta);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConta(@PathVariable Long id){
        contaService.deletarConta(id);
        return ResponseEntity.ok().build();
    }
}
