package AlquimiaSoft.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExcepcionNoEncontrado extends RuntimeException {
    public ExcepcionNoEncontrado(String mensaje) {
        super(mensaje);
    }
}