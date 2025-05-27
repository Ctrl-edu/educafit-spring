package com.example.demo.controller;

import com.example.demo.dto.LoginDTO;
import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioRestController {

    //http:localhost:8080//api/usuario/eliminar-contacto


    @Autowired
    UsuarioServices usuarioServices;

    @GetMapping("/listar-todos")
    public ResponseEntity<?> ListarTodosUsuarios() {
        List<Usuario> ListaTodos = usuarioServices.ListarTodos();
        return ResponseEntity.ok().body(ListaTodos);

    }

    @DeleteMapping("/eliminarUsuario")
    public ResponseEntity<?> eliminarUsuario(@PathVariable("idUsuario") Integer idUsuario) {
        usuarioServices.eliminarUsuario(idUsuario);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/registroUsuario")
    public ResponseEntity<?> registroUsuario(@RequestBody Usuario usuario) {
        try {
            usuarioServices.registroUsuario(usuario);
            return ResponseEntity.ok().body("El usuario " + usuario.getCorreo() + " ha sido creado satisfactoriamente");
        } catch (IllegalArgumentException e) {
            String mensaje = "Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor." + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            boolean loginExitoso = usuarioServices.loginUsuario(loginDTO);
            if (loginExitoso) {
                return ResponseEntity.ok().body("El usuario " + loginDTO.getCorreo() + " ha realizado login exitosamente.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo iniciar sesión con el usuario: " + loginDTO.getCorreo());
            }
        } catch (IllegalArgumentException e) {
            String mensaje = "Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor." + e.getMessage());
        }
    }


}
