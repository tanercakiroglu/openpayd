package com.openpayd.currency.aspect.exceptionhandler;

import com.openpayd.currency.aspect.loggable.Loggable;
import com.openpayd.currency.domain.model.ApiError;
import com.openpayd.currency.exception.BusinessException;
import com.openpayd.currency.exception.ProviderNotFoundException;
import com.openpayd.currency.exception.RemoteCallException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice(basePackages = {"com.openpayd.currency.domain.controller"})
@Loggable
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String SOMETHING_WENT_WRONG = "Something went wrong...";
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ApiError> businessException(BusinessException ex) {
        LOGGER.error("Business Exception", ex);
        final ApiError errors = getApiError(HttpStatus.OK, ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.OK);
    }

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<ApiError> defaultErrorHandler(Exception ex) {
        LOGGER.error("System Exception", ex);
        final ApiError errors = getApiError(HttpStatus.INTERNAL_SERVER_ERROR, SOMETHING_WENT_WRONG);
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = ProviderNotFoundException.class)
    public ResponseEntity<ApiError> providerNotFoundExceptionErrorHandler(ProviderNotFoundException ex) {
        LOGGER.error("System Exception", ex);
        final ApiError errors = getApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = RemoteCallException.class)
    public ResponseEntity<ApiError> remoteCallExceptionErrorHandler(RemoteCallException ex) {
        LOGGER.error("RemoteCallException Exception", ex);
        var statusCode = Integer.parseInt(ex.getMessage());
        var httpStatus = HttpStatus.valueOf(statusCode);
        final ApiError errors = getApiError(httpStatus, httpStatus.getReasonPhrase());
        return new ResponseEntity<>(errors, HttpStatus.valueOf(statusCode));
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ApiError> remoteCallExceptionErrorHandler(IllegalArgumentException ex) {
        LOGGER.error("IllegalArgumentException Exception", ex);
        final ApiError errors = getApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        final ApiError errors = getApiError(HttpStatus.BAD_REQUEST, details);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    private ApiError getApiError(HttpStatus status, String errorMessage) {
        final ApiError errors = new ApiError();
        errors.setTimestamp(LocalDateTime.now());
        errors.setErrors(Collections.singletonList(errorMessage));
        errors.setStatus(status.value());
        return errors;
    }
    private ApiError getApiError(HttpStatus status, List<String> errorMessage) {
        final ApiError errors = new ApiError();
        errors.setTimestamp(LocalDateTime.now());
        errors.setErrors(errorMessage);
        errors.setStatus(status.value());
        return errors;
    }

}
