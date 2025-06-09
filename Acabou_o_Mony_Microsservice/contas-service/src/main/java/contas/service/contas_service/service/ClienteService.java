package contas.service.contas_service.service;


import contas.service.contas_service.config.GerenciadorTokenJwt;
import contas.service.contas_service.dto.cliente.ClienteListarDto;
import contas.service.contas_service.dto.cliente.ClienteTokenDto;
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
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

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
        if (fisicaRepository.existsByEmail(email)){
            return fisicaRepository.getPessoaFisicaByEmail(email);
        }
        throw new ClienteNaoEncontradoException("Cliente não encontrado;");
    }

    public PessoaJuridica buscarPessoaJuridicaPorEmail(String email){
        if (juridicaRepository.existsByEmail(email)){
            return juridicaRepository.getPessoaJuridicaByEmail(email);
        }
        throw new ClienteNaoEncontradoException("Cliente não encontrado;");
    }

    public Cliente buscarClientePorEmail(String email){
        if (clienteRepository.existsByEmail(email)){
            return clienteRepository.getClienteByEmail(email);
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
        String senhaCriptografada = passwordEncoder.encode(fisica.getSenha());
        fisica.setSenha(senhaCriptografada);

        return fisicaRepository.save(fisica);
    }

    public PessoaJuridica cadastrarPessoaJuridica(PessoaJuridica juridica){
        if (juridicaRepository.existsByCnpj(juridica.getCnpj())){
            throw new ClienteConflitoException("CNPJ já cadastrado.");
        }
        String senhaCriptografada = passwordEncoder.encode(juridica.getSenha());
        juridica.setSenha(senhaCriptografada);

        return juridicaRepository.save(juridica);
    }

    public ClienteTokenDto autenticar(Cliente cliente) {


        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                cliente.getEmail(), cliente.getSenha()
        );

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Cliente clienteAutenticado =
                clienteRepository.findByEmail(cliente.getEmail())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return ClienteMapper.of(clienteAutenticado, token);
    }

    public PessoaFisica atualizarPessoaFisica(Long id, PessoaFisica fisica){
        if (fisicaRepository.existsById(id)){
            PessoaFisica pessoaExistente = fisicaRepository.getReferenceById(id);
            pessoaExistente.setEmail(fisica.getEmail());
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
