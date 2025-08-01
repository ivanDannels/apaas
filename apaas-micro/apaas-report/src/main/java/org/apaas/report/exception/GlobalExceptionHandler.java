package org.apaas.report.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ReportException.class)
    public ResponseEntity<Mono<String>> handleReportException(ReportException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Mono.just("Report Error: " + e.getMessage()));
    }
    
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Mono<String>> handleBindException(WebExchangeBindException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Mono.just("Validation Error: " + e.getMessage()));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Mono<String>> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Mono.just("Internal Server Error: " + e.getMessage()));
    }
}