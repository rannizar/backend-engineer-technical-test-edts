package exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConcertNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleConcertNotFound(
            ConcertNotFoundException ex,
            HttpServletRequest request) {

        return buildResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI());
    }

    @ExceptionHandler(BookingClosedException.class)
    public ResponseEntity<ErrorResponse> handleBookingClosed(
            BookingClosedException ex,
            HttpServletRequest request) {

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getRequestURI());
    }

    @ExceptionHandler(TooManyTransactionException.class)
    public ResponseEntity<ErrorResponse> handleBookingTooMany(
            TooManyTransactionException ex,
            HttpServletRequest request) {

        return buildResponse(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                request.getRequestURI());
    }

    @ExceptionHandler(SoldOutException.class)
    public ResponseEntity<ErrorResponse> handleSoldOut(
            SoldOutException ex,
            HttpServletRequest request) {

        return buildResponse(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                request.getRequestURI());
    }

    @ExceptionHandler(DuplicateBookingException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(
            DuplicateBookingException ex,
            HttpServletRequest request) {

        return buildResponse(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                request.getRequestURI());
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ErrorResponse> handleOptimisticLock(
            ObjectOptimisticLockingFailureException ex,
            HttpServletRequest request) {

        return buildResponse(
                HttpStatus.CONFLICT,
                "Too many transaction in a short time, please try again.",
                request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String message = ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                message,
                request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(
            Exception ex,
            HttpServletRequest request) {

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request.getRequestURI());
    }

    private ResponseEntity<ErrorResponse> buildResponse(
            HttpStatus status,
            String message,
            String path) {

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(path)
                .build();

        return ResponseEntity.status(status).body(response);
    }

}
