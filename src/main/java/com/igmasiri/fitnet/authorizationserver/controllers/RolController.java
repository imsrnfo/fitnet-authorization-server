package com.igmasiri.fitnet.authorizationserver.controllers;

import com.igmasiri.fitnet.authorizationserver.models.Rol;
import com.igmasiri.fitnet.authorizationserver.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping(value="/roles")
@Validated
public class RolController extends GenericController{

    @Autowired
    private RolService rolService;

    @PostMapping("/crear")
    public Rol crear(@RequestBody Rol rol) {
        return rolService.save(rol);
    }

    @GetMapping("/buscar/{name}")
    public List<Rol> buscar(@PathVariable @NotBlank @Size(max = 10) String name) {
        return rolService.findByNameContainingIgnoreCase(name);
    }

    @GetMapping("/obtener/{name}")
    public Rol obtener(@PathVariable @NotBlank @Size(max = 10) String name) {
        return rolService.findByName(name);
    }

    @GetMapping("/borrar/{name}")
    public ResponseEntity<String> borrar(@PathVariable @NotBlank @Size(max = 10) String name){
        rolService.delete(rolService.findByName(name));
        return ResponseEntity.ok("El usuario eh sido borrado con exito.");
    }

    @GetMapping("")
    public List<Rol> listar(){
        return rolService.findAll();
    }

}
