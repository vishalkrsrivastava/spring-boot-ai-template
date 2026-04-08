package com.example.template.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

/**
 * Translates exceptions into {@link ErrorResponse} JSON payloads.
 *
 * <ul>
 *   <li>{@link ApiException} subclasses       → their declared HTTP status</li>
 *   <li>{@link MethodArgumentNotValidException}→ 400 with per-field errors</li>
 *   <li>{@link Exception} (catch-all)          → 500 Internal Server Error</li>
 * </ul>
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(
            ApiException ex, HttpServletRequest request) {
        log.warn("API error [{}] on {}: {}", ex.getErrorCode(), request.getRequestURI(), ex.getMessage());
        return ResponseEntity
                .status(ex.getStatus())
                .body(buildError(ex.getErrorCode(), ex.getMessage(), request, null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<ErrorResponse.FieldError> fieldErrors = ex.getBindingResult()
                .getFieldErrors().stream()
                .map(fe -> new ErrorResponse.FieldError(fe.getField(), fe.getDefaultMessage()))
                .toList();
        log.warn("Validation failed on {}: {} error(s)", request.getRequestURI(), fieldErrors.size());
        return ResponseEntity.badRequest().body(
                new ErrorResponse("VALIDATION_ERROR", "Request validation failed",
                        request.getRequestURI(), Instant.now(), fieldErrors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(
            Exception ex, HttpServletRequest request) {
        log.error("Unexpected error on {}", request.getRequestURI(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildError("INTERNAL_ERROR", "An unexpected error occurred", request, null));
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private ErrorResponse buildError(String code, String message,
                                     HttpServletRequest request,
                                     List<ErrorResponse.FieldError> fieldErrors) {
        return new ErrorResponse(code, message, request.getRequestURI(), Instant.now(), fieldErrors);
    }
}
