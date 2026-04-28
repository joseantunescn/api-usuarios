package br.com.cotiinformatica.api_usuarios.exceptions;

public class EmailJaCadastradoException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Email informado ja cadastrado. Tente outro email.";
    }
}


