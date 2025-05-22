package com.acabou_o_mony.mony.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class ContaSalarioNaoPermitidaParaPJException extends RuntimeException {
    public ContaSalarioNaoPermitidaParaPJException(String message) {
    }
}
