package com.mediscreen.patient.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Contains methods that handles exceptions across the whole application.
 *
 * @author Laura Habdul
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * GlobalExceptionHandler logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles exception of the specific type DataAlreadyRegisteredException.
     *
     * @param ex       DataAlreadyRegisteredException object
     * @param @return ResponseEntity error response object and Http Status generated
     */
    @ExceptionHandler(DataAlreadyRegisteredException.class)
    public ResponseEntity handleConflict(final DataAlreadyRegisteredException ex, final WebRequest request)
            throws IOException {
        LOGGER.error("Request - FAILED :", ex);

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    /**
     * Handles exception of the specific type MethodArgumentTypeMismatchException.
     *
     * @param ex      MethodArgumentTypeMismatchException object
     * @param request WebRequest object
     * @return ResponseEntity error response object and Http Status generated
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity handleTypeMismatch(final MethodArgumentTypeMismatchException ex, final WebRequest request) {
        LOGGER.error("Request - FAILED : Invalid id");

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));
        errorDetails.setMessage("Invalid id");

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles exception of the specific type ResourceNotFoundException.
     *
     * @param ex      ResourceNotFoundException object
     * @param request WebRequest object
     * @return ResponseEntity error response object and Http Status generated
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity handleNotFound(final ResourceNotFoundException ex, final WebRequest request) {
        LOGGER.error("Request - FAILED :", ex);
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders header,
                                                       final HttpStatus status, final WebRequest request) {
        LOGGER.error("Request - FAILED :", ex);
        // Get the error messages for invalid fields
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));

        // Create ValidationErrorResponse object using error messages and request details
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), errorMessage, request.getDescription(false));

        return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
