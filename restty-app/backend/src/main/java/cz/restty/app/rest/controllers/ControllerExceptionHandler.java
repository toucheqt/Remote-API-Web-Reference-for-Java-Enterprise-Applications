package cz.restty.app.rest.controllers;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import cz.restty.app.rest.dto.ErrorDto;
import cz.restty.app.rest.dto.RestErrorCode;
import cz.restty.app.rest.exceptions.InvalidSwaggerFileException;
import cz.restty.app.rest.exceptions.ResourceNotFoundException;
import cz.restty.app.rest.exceptions.SwaggerFileUnavailableException;

/**
 * Exception handler for REST API calls.
 * 
 * @author Ondrej Krpec
 *
 */
@RestControllerAdvice("cz.restty.app.rest.controllers")
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * Handler for {@link ResourceNotFoundException}
     * 
     * @param ex
     *            information about exception
     * @param request
     *            information about HTTP request
     * @return error representation
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        logger.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, new ErrorDto(ex.getErrorCode().getValue()), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    /**
     * Handler for {@link InvalidSwaggerFileException}
     * 
     * @param ex
     *            information about exception
     * @param request
     *            information about HTTP request
     * @return error representation
     */
    @ExceptionHandler(InvalidSwaggerFileException.class)
    public ResponseEntity<Object> handleInvalidSwaggerFileException(InvalidSwaggerFileException ex, WebRequest request) {
        logger.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, new ErrorDto(ex.getErrorCode().getValue()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    /**
     * Handler for {@link SwaggerFileUnavailableException}
     * 
     * @param ex
     *            information about exception
     * @param request
     *            information about HTTP request
     * @return error representation
     */
    @ExceptionHandler(SwaggerFileUnavailableException.class)
    public ResponseEntity<Object> handleInvalidSwaggerFileException(SwaggerFileUnavailableException ex, WebRequest request) {
        logger.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, new ErrorDto(ex.getErrorCode().getValue()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    public ResponseEntity<Object> handleDefaultException(Exception ex, WebRequest request) {
        logger.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex,
                new ErrorDto(RestErrorCode.GENERAL_SERVER_ERROR.getValue(), Collections.singletonMap("error", ex.getMessage())),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
