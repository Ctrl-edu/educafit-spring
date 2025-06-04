package com.example.demo.controller;

import com.example.demo.dto.LoginDTO;
import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*")// es decirle al backend que permita las peticiones desde otro dominio

public class UsuarioRestController {
    private static final Logger log = LoggerFactory.getLogger(UsuarioRestController.class);

    @Autowired
    UsuarioServices usuarioServices;

    @GetMapping("/listar-todos")
    public ResponseEntity<?> ListarTodosUsuarios() {
        List<Usuario> ListaTodos = usuarioServices.ListarTodos();
        return ResponseEntity.ok().body(ListaTodos);

    }

    @DeleteMapping("/eliminarUsuario/{correo}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable("correo") String correo) {
        log.info("Inicio de llamado a eliminar usuario en el service con correo: {}", correo);
        usuarioServices.eliminarUsuario(correo);
        log.info("fin de llamado a eliminar usuario con correo: {}", correo);
        return ResponseEntity.ok().build();

    }

    @PutMapping("/actualizarUsuario/{correo}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable("correo") String correo, @RequestBody Usuario usuario) {
        try {
            Usuario usuarioActualizado = usuarioServices.actualizarUsuario(correo, usuario);
            return ResponseEntity.ok().body(usuarioActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    @PostMapping("/registroUsuario")
    public ResponseEntity<?> registroUsuario(@RequestBody Usuario usuario) {
        try {
            usuarioServices.registroUsuario(usuario);
            return ResponseEntity.ok().body("El usuario " + usuario.getCorreo() + " ha sido creado satisfactoriamente");
        } catch (IllegalArgumentException eArrojada) {
            String mensaje = eArrojada.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
        } catch (Exception eDiferente) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor." + eDiferente.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            Usuario usuarioLogin = usuarioServices.loginUsuario(loginDTO);
            if (usuarioLogin != null) {
                return ResponseEntity.ok().body(usuarioLogin);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("usuario o contraseña incorrecta");
            }
        } catch (IllegalArgumentException eArrojada) {
            String mensaje = eArrojada.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
        } catch (Exception eArrojada) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(eArrojada.getMessage());
        }
    }


}
