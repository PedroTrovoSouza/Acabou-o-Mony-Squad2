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

    @GetMapping("/{id}")
    public ResponseEntity<ContaResponseDto> buscarContaPorId(@PathVariable Long id){
        Conta conta = contaService.buscarContaPorId(id);
        ContaResponseDto response = ContaMapper.toResponse(conta);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ContaResponseDto> cadastrarConta(@RequestBody ContaRequestDto contaParaCadastrar){
        Conta conta = contaService.abrirConta(contaParaCadastrar);
        ContaResponseDto response = ContaMapper.toResponse(conta);

        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/conta/{idConta}")
    public ResponseEntity<ContaResponseDto> alterarTipoConta (@PathVariable Long idConta, @RequestBody ContaAtualizacaoDto contaParaAtualizar){
        Conta conta = ContaMapper.toEntity(contaParaAtualizar);
        Conta contaAtualizada = contaService.alterarConta(idConta, conta);
        ContaResponseDto response = ContaMapper.toResponse(contaAtualizada);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<ContaResponseDto> ativarOuDesativarConta(@PathVariable Long id){
        Conta conta = contaService.alterarStatusConta(id);
        ContaResponseDto response = ContaMapper.toResponse(conta);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/saldo/{id}")
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
