package br.com.cotiinformatica.api_usuarios.controllers;

import br.com.cotiinformatica.api_usuarios.dtos.AutenticarRequestDTO;
import br.com.cotiinformatica.api_usuarios.dtos.UsuarioRequestDto;
import br.com.cotiinformatica.api_usuarios.exceptions.EmailJaCadastradoException;
import br.com.cotiinformatica.api_usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.cotiinformatica.api_usuarios.exceptions.AcessoNegadoException;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("criar")
    public ResponseEntity<?> postCriarUsuario(@RequestBody UsuarioRequestDto request) {

        try {
            var response = usuarioService.criarUsuario(request);
            return ResponseEntity.status(201).body(response);
        }
        catch (EmailJaCadastradoException e)    {
            return ResponseEntity.status(400).body(e.getMessage());
        }

        catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno no servidor. Tente novamente mais tarde.");
        }
    }
    @PostMapping("autenticar")
    public ResponseEntity<?> postAutenticarUsuario(@RequestBody AutenticarRequestDTO request) {

        try {
            var response = usuarioService.autenticarUsuario(request);
            return ResponseEntity.ok(response);
        }
        catch (AcessoNegadoException e)    {
            return ResponseEntity.status(401).body(e.getMessage());
        }

        catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno no servidor. Tente novamente mais tarde.");
        }
    }
}
