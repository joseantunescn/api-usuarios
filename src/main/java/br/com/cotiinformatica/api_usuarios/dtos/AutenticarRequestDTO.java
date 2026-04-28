package br.com.cotiinformatica.api_usuarios.dtos;

public record AutenticarRequestDTO(

    String email,
    String senha
) {

}
