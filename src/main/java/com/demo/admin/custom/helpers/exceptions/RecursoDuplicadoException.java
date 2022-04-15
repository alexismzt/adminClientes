package com.demo.admin.custom.helpers.exceptions;

public class RecursoDuplicadoException extends RuntimeException{
    public RecursoDuplicadoException() {
        super("Ocurrio una excepción al intentar duplicar un recurso que debe ser único");
    }
}
