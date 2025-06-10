package contas.service.contas_service.mapper;

import contas.service.contas_service.dto.conta.ContaAtualizacaoDto;
import contas.service.contas_service.dto.conta.ContaRequestDto;
import contas.service.contas_service.dto.conta.ContaResponseDto;
import contas.service.contas_service.entity.Cliente;
import contas.service.contas_service.entity.Conta;

public class ContaMapper {

    public static ContaResponseDto toResponse(Conta conta){
            return new ContaResponseDto(conta.getAgencia(), conta.getTipoConta(), conta.getSaldo(),
                    conta.getIsAtiva(), conta.getCliente().getId());
    }

    public static Conta toEntity(ContaRequestDto dto, Cliente cliente) {
        return new Conta(dto.agencia(), dto.tipoConta(),
                dto.isDebito(), dto.isCredito(), cliente);
    }

    public static Conta toEntity(ContaAtualizacaoDto dto) {
        return new Conta(dto.agencia(), dto.tipoConta());
    }
}
