package org.medx.elixrlabs.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.NoSuchElementException;

/**
 * Global Exception handler for handling exceptions and modifying the outgoing response.
 *
 * @author Gowtham R
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LabException.class)
    public ResponseEntity<ErrorResponse> handleLabException(LabException labException) {
        String initialMessage = labException.getMessage();
        String cause = labException.getCause() == null ? "" : labException.getCause().getMessage();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(initialMessage + (cause.isEmpty() ? "" : ("  Cause : " + cause)))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SlotException.class)
    public ResponseEntity<ErrorResponse> handleSlotException(SlotException slotException) {
        String initialMessage = slotException.getMessage();
        String cause = slotException.getCause() == null ? "" : slotException.getCause().getMessage();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(initialMessage + (cause.isEmpty() ? "" : ("  Cause : " + cause)))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(methodArgumentNotValidException.getAllErrors().getFirst().getDefaultMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(httpMessageNotReadableException.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OTPValidationException.class)
    public ResponseEntity<ErrorResponse> handleOTPExpiredException(OTPValidationException otpValidationException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message(otpValidationException.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException usernameNotFoundException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message(usernameNotFoundException.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AntecedentDateException.class)
    public ResponseEntity<ErrorResponse> handleDateTimeException(AntecedentDateException antecedentDateException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(antecedentDateException.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleDateTimeException(NoSuchElementException noSuchElementException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(noSuchElementException.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException dataIntegrityViolationException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .message(dataIntegrityViolationException.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException noResourceFoundException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message("No URI found. Check the URI and try again!")
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException missingServletRequestParameterException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(missingServletRequestParameterException.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(methodArgumentTypeMismatchException.getMessage() + " Check the type of data entered !")
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
