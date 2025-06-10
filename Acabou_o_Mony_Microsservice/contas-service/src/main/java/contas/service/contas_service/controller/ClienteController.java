package contas.service.contas_service.controller;


import contas.service.contas_service.dto.cliente.*;
import contas.service.contas_service.entity.Cliente;
import contas.service.contas_service.entity.PessoaFisica;
import contas.service.contas_service.entity.PessoaJuridica;
import contas.service.contas_service.mapper.ClienteMapper;
import contas.service.contas_service.service.ClienteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @GetMapping("/pf/email")
    public ResponseEntity<FisicaResponseDto> buscarPessoaFisicaPorEmail(@RequestParam String email){
        PessoaFisica pessoaFisica = clienteService.buscarPessoaFisicaPorEmail(email);
        FisicaResponseDto responseDto = ClienteMapper.toResponseDto(pessoaFisica);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/pj/email")
    public ResponseEntity<JuridicaResponseDto> buscarPessoaJuridicaPorEmail(@RequestParam String email){
        PessoaJuridica pessoaJuridica = clienteService.buscarPessoaJuridicaPorEmail(email);
        JuridicaResponseDto responseDto = ClienteMapper.toResponseDto(pessoaJuridica);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/email")
    public ResponseEntity<ClienteDto> buscarClientePorEmail(@RequestParam String email){
        Cliente cliente = clienteService.buscarClientePorEmail(email);
        ClienteDto dto = new ClienteDto(cliente.getId(), cliente.getNome(), cliente.getLogin(), cliente.getPassword());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/pf/{id}")
    public ResponseEntity<FisicaResponseDto> buscarPessoaFisicaPorId(@PathVariable Long id){
        PessoaFisica pessoaFisica = clienteService.buscarPessoaFisicaPorId(id);
        FisicaResponseDto responseDto = ClienteMapper.toResponseDto(pessoaFisica);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/pj/{id}")
    public ResponseEntity<JuridicaResponseDto> buscarPessoaJuridicaPorId(@PathVariable Long id){
        PessoaJuridica pessoaJuridica = clienteService.buscarPessoaJuridicaPorId(id);
        JuridicaResponseDto responseDto = ClienteMapper.toResponseDto(pessoaJuridica);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/cadastro/pf")
    public ResponseEntity<FisicaResponseDto> cadastrarPessoaFisica(@RequestBody @Valid FisicaRequestDto dtoCadastro){
        try {
            final PessoaFisica fisica = ClienteMapper.toEntity(dtoCadastro);
            PessoaFisica clienteCadastrado = clienteService.cadastrarPessoaFisica(dtoCadastro);
            FisicaResponseDto response = ClienteMapper.toResponseDto(clienteCadastrado);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    @PostMapping("/cadastro/pj")
    public ResponseEntity<JuridicaResponseDto> cadastrarPessoaJuridica(@RequestBody @Valid JuridicaRequestDto dtoCadastro){
        final PessoaJuridica juridica = ClienteMapper.toEntity(dtoCadastro);
        PessoaJuridica clienteCadastrado = clienteService.cadastrarPessoaJuridica(juridica);
        JuridicaResponseDto response = ClienteMapper.toResponseDto(clienteCadastrado);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody ClienteLoginDto clienteLoginDto) {
        String clienteTokenDto = clienteService.autenticar(clienteLoginDto);

        return ResponseEntity.status(200).body(clienteTokenDto);
    }

    @GetMapping
    public ResponseEntity<List<ClienteListarDto>> listarTodos() {
        List<ClienteListarDto> clientesEncontrados = this.clienteService.listarTodos();

        if (clientesEncontrados.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(clientesEncontrados);
    }

    @PutMapping("/pf/{id}")
    public ResponseEntity<FisicaResponseDto> atualizarPessoaFisica(@PathVariable Long id,@RequestBody FisicaAtualizacaoDto dtoAtualizacao){
        PessoaFisica fisica = ClienteMapper.toEntity(dtoAtualizacao);
        PessoaFisica clienteAtualizado = clienteService.atualizarPessoaFisica(id, fisica);
        FisicaResponseDto response = ClienteMapper.toResponseDto(clienteAtualizado);
        return ResponseEntity.ok(response);
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