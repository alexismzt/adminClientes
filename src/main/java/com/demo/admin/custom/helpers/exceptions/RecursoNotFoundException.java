package com.demo.admin.custom.helpers.exceptions;

public class RecursoNotFoundException extends RuntimeException{
    public RecursoNotFoundException() {
        super("No se encontro el recurso con los criterios dados");
    }

    public RecursoNotFoundException(String message) {
        super(message);
    }
}
