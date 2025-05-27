package contas.service.contas_service.controller;


import contas.service.contas_service.dto.cliente.*;
import contas.service.contas_service.entity.PessoaFisica;
import contas.service.contas_service.entity.PessoaJuridica;
import contas.service.contas_service.mapper.ClienteMapper;
import contas.service.contas_service.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;

    @GetMapping("/pf")
    public ResponseEntity<List<FisicaResponseDto>> listarPessoaFisica(){
        List<PessoaFisica> fisicas = clienteService.listarPessoaFisica();
        List<FisicaResponseDto> response = fisicas.stream().map(ClienteMapper::toResponseDto).toList();

        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping("/pj")
    public ResponseEntity<List<JuridicaResponseDto>> listarPessoaJuridica(){
        List<PessoaJuridica> juridicas = clienteService.listarPessoaJuridica();
        List<JuridicaResponseDto> response = juridicas.stream().map(ClienteMapper::toResponseDto).toList();

        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @PostMapping("/pf")
    public ResponseEntity<FisicaResponseDto> cadastrarPessoaFisica(@RequestBody @Valid FisicaRequestDto dtoCadastro){
        PessoaFisica fisica = ClienteMapper.toEntity(dtoCadastro);
        PessoaFisica clienteCadastrado = clienteService.cadastrarPessoaFisica(fisica);
        FisicaResponseDto response = ClienteMapper.toResponseDto(clienteCadastrado);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/pj")
    public ResponseEntity<JuridicaResponseDto> cadastrarPessoaJuridica(@RequestBody @Valid JuridicaRequestDto dtoCadastro){
        PessoaJuridica juridica = ClienteMapper.toEntity(dtoCadastro);
        PessoaJuridica clienteCadastrado = clienteService.cadastrarPessoaJuridica(juridica);
        JuridicaResponseDto response = ClienteMapper.toResponseDto(clienteCadastrado);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/pf/{id}")
    public ResponseEntity<FisicaResponseDto> atualizarPessoaFisica(@PathVariable Long id,@RequestBody FisicaAtualizacaoDto dtoAtualizacao){
        PessoaFisica fisica = ClienteMapper.toEntity(dtoAtualizacao);
        PessoaFisica clienteAtualizado = clienteService.atualizarPessoaFisica(id, fisica);
        FisicaResponseDto response = ClienteMapper.toResponseDto(clienteAtualizado);
        return ResponseEntity.status(201).body(response);
    }

    @DeleteMapping("/pf/{id}")
    public ResponseEntity<Void> deletarPessoaFisica(@PathVariable String cpf){
        clienteService.deletarPessoaPorCpf(cpf);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/pj/{id}")
    public ResponseEntity<Void> deletarPessoaJuridica(@PathVariable String cnpj){
        clienteService.deletarEmpresaPorCnpj(cnpj);
        return ResponseEntity.ok().build();
    }

}
