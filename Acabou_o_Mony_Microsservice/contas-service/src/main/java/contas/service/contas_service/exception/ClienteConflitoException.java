package contas.service.contas_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class ClienteConflitoException extends RuntimeException {
    public ClienteConflitoException(String message) {
    }
}
