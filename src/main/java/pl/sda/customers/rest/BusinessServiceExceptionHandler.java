package pl.sda.customers.rest;

import java.time.Instant;
import javax.persistence.EntityNotFoundException;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.sda.customers.exception.BusinessServiceException;

@ControllerAdvice
final class BusinessServiceExceptionHandler {

    @Value
    static class ErrorMessage {
        String message;
        Instant errorTime;
    }

    @ExceptionHandler(BusinessServiceException.class)
    public ResponseEntity<ErrorMessage> handle(BusinessServiceException ex) {
        return ResponseEntity.badRequest().body(new ErrorMessage(ex.getMessage(), Instant.now()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handle(EntityNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }
}
