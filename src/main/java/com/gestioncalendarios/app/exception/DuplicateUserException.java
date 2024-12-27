package com.gestioncalendarios.app.exception;

public class DuplicateUserException extends RuntimeException{
    public DuplicateUserException(String message) {
        super(message); // El mensaje será el que se devolverá al frontend
    }
}
