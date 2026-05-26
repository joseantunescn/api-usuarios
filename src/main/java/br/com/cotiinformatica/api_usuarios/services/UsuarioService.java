package br.com.cotiinformatica.api_usuarios.services;

import br.com.cotiinformatica.api_usuarios.components.CryptoComponent;
import br.com.cotiinformatica.api_usuarios.components.JwtTokenComponent;
import br.com.cotiinformatica.api_usuarios.dtos.*;
import br.com.cotiinformatica.api_usuarios.entities.Usuario;
import br.com.cotiinformatica.api_usuarios.enums.Perfil;
import br.com.cotiinformatica.api_usuarios.exceptions.AcessoNegadoException;
import br.com.cotiinformatica.api_usuarios.exceptions.EmailJaCadastradoException;
import br.com.cotiinformatica.api_usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CryptoComponent cryptoComponent;

    @Autowired
    private JwtTokenComponent jwtTokenComponent;

    public UsuarioResponseDto criarUsuario(UsuarioRequestDto request) {

        //verify se o email ja existe no banco de dados
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new EmailJaCadastradoException();
        }

        //Criando um objeto da classe de entidade
        var usuario = new Usuario();

        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setSenha(cryptoComponent.getSha256(request.senha()));
        usuario.setPerfil(Perfil.OPERADOR);

        //Salvar no banco de dados
        usuarioRepository.save(usuario);

        //Retornar os dados de resposta
        return new UsuarioResponseDto(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                LocalDateTime.now(),
                usuario.getPerfil().toString()
        );
    }

    //method to authenticate user
    public AutenticarResponseDTO autenticarUsuario(AutenticarRequestDTO request) {
        var email = request.email();
        var senha = cryptoComponent.getSha256(request.senha());

        var usuario = usuarioRepository.findByEmailAndSenha(email, senha);

        if (usuario == null) {
            throw new AcessoNegadoException();

        }

        // generate the jwt token for the authenticated user
        var token = jwtTokenComponent.getToken(usuario.getId(), usuario.getEmail(), usuario.getPerfil().toString());


        return new AutenticarResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                LocalDateTime.now(),
                usuario.getPerfil().toString(),
                token
        );
    }

    public DadosUsuarioResponseDto ObterDadosDoUsuario(UUID id) {

        //Consultar o usuário no banco de dados através do id
        var usuario = usuarioRepository.findById(id).get();

        //Retornar os dados do usuário
        return new DadosUsuarioResponseDto(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfil().toString()
        );
    }
}