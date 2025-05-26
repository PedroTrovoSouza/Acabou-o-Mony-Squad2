package com.acabou_o_mony.mony.dto;

import com.acabou_o_mony.mony.dto.cartao.CartaoResponseDTO;
import com.acabou_o_mony.mony.enums.StatusTransacao;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransacaoResponseDTO {
    private Long id;
    private String tipoTransacao;
    private Double valor;
    private LocalDateTime dthora;
    private int destinatario;
    private StatusTransacao status;
    private Long remetenteId;
    private CartaoResponseDTO cartao;
    private Long contaId;
}
