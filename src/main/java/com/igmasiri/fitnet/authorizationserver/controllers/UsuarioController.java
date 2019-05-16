package com.igmasiri.fitnet.authorizationserver.controllers;

import com.igmasiri.fitnet.authorizationserver.models.Usuario;
import com.igmasiri.fitnet.authorizationserver.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping(value="/usuarios")
@Validated
public class UsuarioController extends GenericController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/crear")
    public Usuario crear(@RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }

    @GetMapping("/buscar/{username}")
    public List<Usuario> buscar(@PathVariable @NotBlank @Size(max = 10) String username) {
        return usuarioService.findByUsernameContainingIgnoreCase(username);
    }

    @GetMapping("/obtener/{username}")
    public Usuario obtener(@PathVariable @NotBlank @Size(max = 10) String username) {
        return usuarioService.findByUsername(username);
    }

    @GetMapping("/borrar/{username}")
    public ResponseEntity<String> borrar(@PathVariable @NotBlank @Size(max = 10) String username){
        usuarioService.delete(usuarioService.findByUsername(username));
        return ResponseEntity.ok("El usuario eh sido borrado con exito.");
    }

    @GetMapping("")
    public List<Usuario> listar(){
        return usuarioService.findAll();
    }

}
