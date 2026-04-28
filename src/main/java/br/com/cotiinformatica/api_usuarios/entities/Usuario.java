package br.com.cotiinformatica.api_usuarios.entities;

import br.com.cotiinformatica.api_usuarios.enums.Perfil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "USUARIOS")
@Setter
@Getter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "NOME", length = 150, nullable = false)
    private String nome;

    @Column(name = "EMAIL", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "SENHA", length = 100, nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "PERFIL", nullable = false)
    private Perfil perfil;
}
