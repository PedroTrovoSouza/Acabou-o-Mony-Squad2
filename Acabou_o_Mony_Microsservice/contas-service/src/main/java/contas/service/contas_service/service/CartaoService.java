package contas.service.contas_service.service;

import contas.service.contas_service.dto.cartao.CartaoRequestDTO;
import contas.service.contas_service.dto.cartao.CartaoResponseDTO;
import contas.service.contas_service.entity.Cartao;
import contas.service.contas_service.mapper.CartaoMapper;
import contas.service.contas_service.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartaoService {

    @Autowired
    private CartaoMapper mapper;

    @Autowired
    private CartaoRepository repository;

    public CartaoResponseDTO salvarCartao(CartaoRequestDTO dto) {
        Cartao cartaoEntidade = mapper.toEntity(dto);
        Optional<Cartao> cartaoExistente = repository.findById(cartaoEntidade.getId());

        if (cartaoExistente.isEmpty()) {
            return null;
        }

        Cartao cartaoSalvar = repository.save(cartaoEntidade);
        return mapper.toResponseDTO(cartaoSalvar);
    }

    public CartaoResponseDTO listarCartaoPorId(Long id) {
        Optional<Cartao> cartaoPorId = repository.findById(id);
        if(cartaoPorId.isEmpty()) {
            return null;
        }

        Cartao cartao = cartaoPorId.get();
        return mapper.toResponseDTO(cartao);
    }

    public CartaoResponseDTO atualizarCartao(Long id, CartaoRequestDTO dto) {
        Cartao existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado com ID: " + id));

        existente.setNome(dto.getNome());
        existente.setNumero(dto.getNumero());
        existente.setVencimento(dto.getVencimento());
        existente.setCvv(dto.getCvv());
        existente.setBandeira(dto.getBandeira());
        existente.setCredito(dto.isCredito());
        existente.setDebito(dto.isDebito());
        existente.setLimiteCredito(dto.getLimiteCredito());
        existente.setContaId(dto.getContaId());

        Cartao atualizado = repository.save(existente);
        return mapper.toResponseDTO(atualizado);
    }


    public boolean deletarPorId (Long id) {
        Optional<Cartao> cartaoDeletado = repository.findById(id);

        if (cartaoDeletado.isEmpty()){
            return false;
        }

        repository.delete(cartaoDeletado.get());
        return true;
    }
}
