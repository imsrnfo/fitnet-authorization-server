package com.igmasiri.fitnet.authorizationserver.controllers;

import com.igmasiri.fitnet.authorizationserver.config.exceptions.AplicationHandledExceptionDTO;
import com.igmasiri.fitnet.authorizationserver.models.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/usuarios")
@Validated
public class UsuarioController {

    @PostMapping("/crear")
    ResponseEntity<String> crear(@RequestBody Usuario usuario) {
        // persisting the user
        return ResponseEntity.ok("User is valid");
    }

    @GetMapping(name = "buscar/{username}")
    public List<Usuario> buscar(@PathVariable @NotBlank @Size(max = 10) String username) {
        return null;
    }

    @GetMapping(name = "obtener/{username}")
    public Usuario obtener(@PathVariable @NotBlank @Size(max = 10) String username) {
        return null;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    AplicationHandledExceptionDTO mostrarExcepcion(Exception e) {

        AplicationHandledExceptionDTO result = new AplicationHandledExceptionDTO();
        result.setStatus("Error");
        result.setMensaje(e.getMessage());

        if (e instanceof ConstraintViolationException){
            List<String> validationExceptions = ((ConstraintViolationException)e).getConstraintViolations()
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());
            result.setMensaje(validationExceptions.stream().map(n -> n.toString()).collect(Collectors.joining(",")));
        }

        return result;
    }
}
