package br.com.cotiinformatica.api_usuarios.dtos;

public record UsuarioRequestDto(
        String nome,
        String email,
        String senha
) {
}
