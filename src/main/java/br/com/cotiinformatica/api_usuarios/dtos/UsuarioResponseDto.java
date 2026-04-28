package br.com.cotiinformatica.api_usuarios.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioResponseDto(
        UUID id,
        String nome,
        String email,
        LocalDateTime dataHora,
        String perfil
) {
}
