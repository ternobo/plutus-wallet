package com.ternobo.wallet.errors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, Exception.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            Exception e, WebRequest request) throws Exception {
//        throw e;
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
        Map<String, Object> body = new HashMap<>();
        List<String> errors = ex.getBindingResult()
                .getGlobalErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        body.put("path", servletRequest.getServletPath());
        body.put("errors", errors);
        body.put("status", 400);
        body.put("timestamp", new Date());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}