package com.example;

public class CircularDependencyException extends RuntimeException{
    public CircularDependencyException(String message) {
        super(message);
    }
}
