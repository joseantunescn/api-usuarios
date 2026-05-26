package br.com.cotiinformatica.api_usuarios.components;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenComponent {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expiration;

    /*
     * Método para calcular e retornar a data de expiração do token
     */
    public Date getExpiration() {
        // Data atual + o tempo de expiração em milisegundos
        var dataAtual = new Date();
        return new Date(dataAtual.getTime() + Integer.parseInt(expiration));
    }

    /*
     * Método para gerar e retornar o Token do usuário autenticado
     */
    public String getToken(UUID usuarioId, String email, String perfil) {

        return Jwts.builder()
                .setSubject(usuarioId.toString()) // identificação do usuário do token
                .claim("email", email) // email do usuário
                .claim("perfil", perfil) // perfil do usuário
                .setIssuedAt(new Date()) // data de geração do token
                .setExpiration(getExpiration()) // data de expiração do token
                .signWith(SignatureAlgorithm.HS256, secret) // chave de assinatura
                .compact(); // finaliza e retorna o token gerado
    }

    // method to extract the user id from the token
    public UUID getUserId(HttpServletRequest http) {

        try {
            // Obter o cabeçalho Authorization
            String authorization = http.getHeader("Authorization");
            if (authorization == null|| !authorization.startsWith("Bearer ")) {
                return null;
            }

            // Extrair o token (remove o "Bearer ")
            String token = authorization.replace("Bearer ", "");

            // Parse do token
            Claims claims = Jwts.parser().setSigningKey(secret.getBytes())
                    .parseClaimsJws(token).getBody();

            // Retornar a claim "name"
            var user = claims.get("name", String.class);

            return UUID.fromString(user);
        } catch (Exception e) {
            // Token inválido ou ausente
            return null;
        }
    }
}


