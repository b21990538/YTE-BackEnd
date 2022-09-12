package yte.thebackend.common.controllerAdvice;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import yte.thebackend.common.response.MessageResponse;
import yte.thebackend.common.response.ResultType;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptions {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<MessageResponse> handleValidationErrors(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new MessageResponse(exception.getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining("\n")), ResultType.ERROR));
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<MessageResponse> handleValidationErrors(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new MessageResponse(exception.getMessage(), ResultType.ERROR));
    }
}
