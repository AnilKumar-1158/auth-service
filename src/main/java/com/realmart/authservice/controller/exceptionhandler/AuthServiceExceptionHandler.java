package com.realmart.authservice.controller.exceptionhandler;

import com.realmart.authservice.exception.DuplicateUserException;
import com.realmart.authservice.model.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class AuthServiceExceptionHandler {

    @ExceptionHandler(exception = DuplicateUserException.class)
    public ResponseEntity<ApiError> handleDuplicateUserException(DuplicateUserException ex, HttpServletRequest request){
        ApiError errorDetails = ApiError.builder()
                .message(ex.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .path(request.getRequestURI())
                .timestamp(Instant.now())
                .httpMethod(request.getMethod())
                .build();
   return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(exception = Exception.class)
    public ResponseEntity<ApiError> authServiceException(Exception ex, HttpServletRequest request){
        ApiError errorDetails = ApiError.builder()
                .message("An unexpected error occurred")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .path(request.getRequestURI())
                .timestamp(Instant.now())
                .httpMethod(request.getMethod())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(exception = BadCredentialsException.class)
    public ResponseEntity<ApiError> loginAuthException(HttpServletRequest request){
        ApiError errorDetails = ApiError.builder()
                .message("Invalid email or password")
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .path(request.getRequestURI())
                .timestamp(Instant.now())
                .httpMethod(request.getMethod())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(exception = DisabledException.class)
    public ResponseEntity<ApiError> loginDisabledException(HttpServletRequest request){
        ApiError errorDetails = ApiError.builder()
                .message("Account Disabled")
                .status(HttpStatus.FORBIDDEN.value())
                .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                .path(request.getRequestURI())
                .timestamp(Instant.now())
                .httpMethod(request.getMethod())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }
}
