package com.mediscreen.ui.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

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
     * Handles exception of the specific type MethodArgumentTypeMismatchException.
     *
     * @param ex      MethodArgumentTypeMismatchException object
     * @param request WebRequest object
     * @return ResponseEntity error response object and Http Status generated
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity handleTypeMismatch(final MethodArgumentTypeMismatchException ex, final WebRequest request) {
        LOGGER.error("Request - FAILED : Invalid id");
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        errorDetails.setMessage("Invalid id");
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles exception of the specific type ResourceNotFoundException.
     *
     * @param ex      ResourceNotFoundException object
     * @param request WebRequest object
     * @return ResponseEntity error response object and Http Status generated
     */
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity handleNotFound(final ResourceNotFoundException ex, final WebRequest request) {
        LOGGER.error("Request - FAILED :", ex);
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
    }
}