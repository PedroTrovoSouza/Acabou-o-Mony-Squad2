package com.acabou_o_mony.mony.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class JaPossuiCartaoException extends RuntimeException {
    public JaPossuiCartaoException(String message) {
        super(message);
    }
}
