package se.lexicon.g48emailsenderapi.exception;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.UUID;
import java.util.stream.Collectors;


//Todo: Check Why exception handler class not working
// UPDATE: The problem was because of the absence of getters in the ErrorDTO

@ControllerAdvice
public class RESTExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String errorDetail = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ErrorDTO dto = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), errorDetail);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);

    }

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> illegalArgumentException(IllegalArgumentException ex) {
        ErrorDTO dto = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<ErrorDTO> handleEmailException(EmailException ex) {
        ErrorDTO dto = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }

    // add more methods to catch other exceptions as needed...


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGlobalException(Exception ex) {
        String uuid = UUID.randomUUID().toString();
        System.out.println("------------------------");
        System.out.println("ERROR ID: " + uuid);
        ex.printStackTrace();
        System.out.println("------------------------");

        ErrorDTO dto = new ErrorDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Call Support Team. ERROR ID:" + uuid);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto);
    }

}
