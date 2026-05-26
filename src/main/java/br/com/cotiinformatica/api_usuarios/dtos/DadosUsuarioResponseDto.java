package br.com.cotiinformatica.api_usuarios.dtos;

import java.util.UUID;

public record DadosUsuarioResponseDto(
        UUID id,
        String nome,
        String email,
        String perfil
) {
}
