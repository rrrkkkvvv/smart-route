package com.rrrkkkvvv.smartRoute.shared.exception;

import com.rrrkkkvvv.smartRoute.auth.domain.InvalidCredentialsException;
import com.rrrkkkvvv.smartRoute.auth.domain.UserAlreadyExistsException;
import com.rrrkkkvvv.smartRoute.location.domain.LocationNotFoundException;
import com.rrrkkkvvv.smartRoute.route.domain.UnsupportedRouteProfileException;
import com.rrrkkkvvv.smartRoute.user.domain.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(
            Exception ex
    ){
        ErrorResponseDTO errorDTO = new ErrorResponseDTO("Internal server error");
        log.error("Handle exception ",ex);
        return ResponseEntity
                .status(500)
                .body(errorDTO);
    }
    @ExceptionHandler(exception = {InvalidCredentialsException.class, UserAlreadyExistsException.class})
    public ResponseEntity<?> handleInvalidCredentials(RuntimeException ex) {
        log.error("Handle exception ",ex);
        return ResponseEntity.status(409).body(new ErrorResponseDTO(ex.getMessage() ));
    }

    @ExceptionHandler(exception ={MissingServletRequestParameterException.class})
    public ResponseEntity<?> handleParameterMissing(MissingServletRequestParameterException  ex) {
        log.error("Handle exception ",ex);
        return ResponseEntity.status(400).body(new ErrorResponseDTO(ex.getMessage() ));
    }
    @ExceptionHandler(exception ={UserNotFoundException.class, LocationNotFoundException.class})
    public ResponseEntity<?> handleNotFound(RuntimeException ex) {
        log.error("Handle exception ",ex);
        return ResponseEntity.status(404).body(new ErrorResponseDTO(ex.getMessage() ));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(
            MethodArgumentNotValidException ex
    ) {
        log.error("Handle exception ",ex);

        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        error -> error.getField(),
                        error -> error.getDefaultMessage(),
                        (first, second) -> first
                ));

        ValidationErrorResponse response = new ValidationErrorResponse(
                "Validation failed",
                errors
        );

        return ResponseEntity
                .status(400)
                .body(response);
    }
    @ExceptionHandler(UnsupportedRouteProfileException.class)
    public ResponseEntity<?> handleUnsupportedProfile(
            UnsupportedRouteProfileException ex
    ) {
        log.error("Handle exception ",ex);

        return ResponseEntity
                .unprocessableContent()
                .body(ex.getMessage());
    }

    @ExceptionHandler(ExternalProviderException.class)
    public ResponseEntity<?> handleExternalProvider(
            ExternalProviderException ex
    ) {
        log.error("Handle exception ",ex);

        return ResponseEntity
                .status(502)
                .body(ex.getMessage());
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<?> handleUnsupportedOperation(
            UnsupportedOperationException ex
    ) {
        log.error("Handle exception ",ex);

        return ResponseEntity
                .status(400)
                .body(ex.getMessage());
    }

}
