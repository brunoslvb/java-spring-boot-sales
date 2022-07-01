package br.com.sales.api.controllers;

import br.com.sales.api.ApiError;
import br.com.sales.exceptions.BusinessRuleException;
import br.com.sales.exceptions.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(BusinessRuleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBusinessRuleException(BusinessRuleException ex){
        return new ApiError(ex.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleOrderNotFoundException(OrderNotFoundException ex){
        return new ApiError(ex.getMessage());
    }

}
