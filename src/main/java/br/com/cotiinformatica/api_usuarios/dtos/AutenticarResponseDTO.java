package br.com.cotiinformatica.api_usuarios.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record AutenticarResponseDTO(
        UUID id,
        String nome,
        String email,
        LocalDateTime dataHoraAcesso,
        String perfil,
        String token


) {

}
