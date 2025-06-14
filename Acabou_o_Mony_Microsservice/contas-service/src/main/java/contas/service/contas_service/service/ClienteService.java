package contas.service.contas_service.service;


import contas.service.contas_service.dto.cliente.ClienteListarDto;
import contas.service.contas_service.dto.cliente.ClienteLoginDto;
import contas.service.contas_service.dto.cliente.FisicaRequestDto;
import contas.service.contas_service.dto.cliente.FisicaResponseDto;
import contas.service.contas_service.entity.Cliente;
import contas.service.contas_service.entity.PessoaFisica;
import contas.service.contas_service.entity.PessoaJuridica;
import contas.service.contas_service.exception.ClienteConflitoException;
import contas.service.contas_service.exception.ClienteNaoEncontradoException;
import contas.service.contas_service.mapper.ClienteMapper;
import contas.service.contas_service.repository.ClienteRepository;
import contas.service.contas_service.repository.PessoaFisicaRepository;
import contas.service.contas_service.repository.PessoaJuridicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ClienteService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    private final PessoaJuridicaRepository juridicaRepository;
    private final PessoaFisicaRepository fisicaRepository;
    private final ClienteRepository clienteRepository;

    public List<PessoaFisica> listarPessoaFisica(){
        return fisicaRepository.findAll();
    }

    public List<PessoaJuridica> listarPessoaJuridica(){
        return juridicaRepository.findAll();
    }

    public List<ClienteListarDto> listarTodos() {
        List<Cliente> clientesEncontrados = clienteRepository.findAll();
        return clientesEncontrados.stream().map(ClienteMapper::of).toList();
    }

    public PessoaFisica buscarPessoaFisicaPorId(Long id){
        return fisicaRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Pessoa Fisica não enconntrada"));
    }

    public PessoaJuridica buscarPessoaJuridicaPorId(Long id){
        return juridicaRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Pessoa Fisica não enconntrada"));
    }

    public PessoaFisica buscarPessoaFisicaPorEmail(String email){
        if (fisicaRepository.existsByLogin(email)){
            return fisicaRepository.getPessoaFisicaByLogin(email);
        }
        throw new ClienteNaoEncontradoException("Cliente não encontrado;");
    }

    public PessoaJuridica buscarPessoaJuridicaPorEmail(String email){
        if (juridicaRepository.existsByLogin(email)){
            return juridicaRepository.getPessoaJuridicaByLogin(email);
        }
        throw new ClienteNaoEncontradoException("Cliente não encontrado;");
    }

    public Cliente buscarClientePorEmail(String email){
        if (clienteRepository.existsByLogin(email)){
            return clienteRepository.getClienteByLogin(email);
        }
        throw new ClienteNaoEncontradoException("Cliente não encontrado;");
    }

    public Cliente buscarClientePorId(Long id){
        if (clienteRepository.existsById(id)){
            return clienteRepository.getReferenceById(id);
        }
        throw new ClienteNaoEncontradoException("Cliente não encontrado;");
    }

    public PessoaFisica cadastrarPessoaFisica(PessoaFisica fisica){
        if (fisicaRepository.existsByCpf(fisica.getCpf())){
            throw new ClienteConflitoException("CPF já cadastrado.");
        }
        String senhaCriptografada = passwordEncoder.encode(fisica.getPassword());
        fisica.setPassword(senhaCriptografada);

        return  fisicaRepository.save(fisica);
    }

    public PessoaJuridica cadastrarPessoaJuridica(PessoaJuridica juridica){
        if (juridicaRepository.existsByCnpj(juridica.getCnpj())){
            throw new ClienteConflitoException("CNPJ já cadastrado.");
        }
        String senhaCriptografada = passwordEncoder.encode(juridica.getPassword());
        juridica.setPassword(senhaCriptografada);

        return juridicaRepository.save(juridica);
    }

    public String autenticar(ClienteLoginDto cliente) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(cliente.getEmail(), cliente.getSenha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((Cliente) auth.getPrincipal());

        return token;
    }

    public PessoaFisica atualizarPessoaFisica(Long id, PessoaFisica fisica){
        if (fisicaRepository.existsById(id)){
            PessoaFisica pessoaExistente = fisicaRepository.getReferenceById(id);
            pessoaExistente.setLogin(fisica.getLogin());
            pessoaExistente.setDataNascimento(fisica.getDataNascimento());
            pessoaExistente.setGenero(fisica.getGenero());
            return fisicaRepository.save(pessoaExistente);
        }
        throw new ClienteNaoEncontradoException("Pessoa Física não encontrado;");
    }

    public PessoaFisica buscarPessoaPorCpf(String cpf){
        if (fisicaRepository.existsByCpf(cpf)){
            return fisicaRepository.getPessoaFisicaByCpf(cpf);
        }
        throw new ClienteNaoEncontradoException("Cliente não encontrado;");
    }
    public PessoaJuridica buscarEmpresaPorCnpj(String cnpj){
        if (juridicaRepository.existsByCnpj(cnpj)){
            return juridicaRepository.getPessoaJuridicaByCnpj(cnpj);
        }
        throw new ClienteNaoEncontradoException("Cliente não encontrado;");
    }

    public void deletarPessoaPorCpf(String cpf){
        PessoaFisica cliente = buscarPessoaPorCpf(cpf);
        fisicaRepository.deleteById(cliente.getId());
    }
    public void deletarEmpresaPorCnpj(String cnpj){
        PessoaJuridica cliente = buscarEmpresaPorCnpj(cnpj);
        juridicaRepository.deleteById(cliente.getId());
    }
}
