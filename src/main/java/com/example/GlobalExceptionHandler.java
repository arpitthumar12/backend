package com.example;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CircularDependencyException.class)
    @ResponseBody
    public String handleCircularDependencyException(CircularDependencyException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public String handleNullPointerException(NullPointerException ex) {
        return "Please ensure all required fields are provided and are not null.";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public String handleIllegalArgumentException(IllegalArgumentException ex) {
        return "An argument provided was not in the expected format: " + ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    @ResponseBody
    public String handleNumberFormatException(NumberFormatException ex) {
        return "Numerical fields must be formatted as valid numbers.";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({JsonParseException.class, JsonMappingException.class})
    @ResponseBody
    public String handleJsonProcessingException(JsonProcessingException ex) {
        return "The provided JSON was malformed: " + ex.getMessage();
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public String handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return "HTTP method not supported: " + ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormulaException.class)
    @ResponseBody
    public String handleInvalidFormulaException(InvalidFormulaException ex) {
        return ex.getMessage();
    }

}
