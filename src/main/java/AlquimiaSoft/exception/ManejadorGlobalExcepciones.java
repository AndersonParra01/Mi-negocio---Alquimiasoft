package AlquimiaSoft.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ManejadorGlobalExcepciones {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> onValidacion(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage));
    }

    // Opcional: capturar excepciones de negocio directamente
    @ExceptionHandler(ExcepcionNegocio.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> onNegocio(ExcepcionNegocio ex) {
        return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(ExcepcionNoEncontrado.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> onNoEncontrado(ExcepcionNoEncontrado ex) {
        return Map.of("error", ex.getMessage());
    }
}