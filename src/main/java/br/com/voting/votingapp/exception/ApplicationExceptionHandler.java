package br.com.voting.votingapp.exception;

import java.net.SocketTimeoutException;

import javax.servlet.ServletException;
import javax.validation.ConstraintViolationException;

import br.com.voting.votingapp.dto.response.ErrorResponseDTO;
import com.fasterxml.jackson.core.JacksonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;

@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleCustomerException(NotFoundException e) {
        log.error(e.getMessage(), e);
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RestTemplateRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleRestException(RestTemplateRequestException e) {
        log.error("RestTemplateRequestException - " + e.getMessage(), e);
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Internal Request Error", HttpStatus.FAILED_DEPENDENCY.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class,
                       ServletException.class, IllegalArgumentException.class, SessionEndedException.class, UserAlreadyVotedException.class,
                       SessionAlreadyActiveException.class, UserNotAbleToVoteException.class, EndDateBeforeCurrentDateException.class,
                       JacksonException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(Exception e) {
        log.error(e.getMessage(), e);
        String message = e instanceof BusinessException ? e.getMessage() : "Bad Request";
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(message, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SocketTimeoutException.class)
    public ResponseEntity<ErrorResponseDTO> handleSocketTimeoutException(SocketTimeoutException e) {
        log.error(e.getMessage(), e);
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Request timeout", HttpStatus.REQUEST_TIMEOUT.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.REQUEST_TIMEOUT);
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceAccessException(ResourceAccessException e) {
        log.error(e.getMessage(), e);
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Failed dependency", HttpStatus.FAILED_DEPENDENCY.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception e) {
        log.error("Internal server error - " + e.getMessage(), e);
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
