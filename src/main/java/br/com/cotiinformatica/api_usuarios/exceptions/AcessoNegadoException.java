package br.com.cotiinformatica.api_usuarios.exceptions;

public class AcessoNegadoException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Acesso negado. Usuario invalido";
    }
}
