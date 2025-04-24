package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/guardarUsuario")
    public ResponseEntity<?> guardarUsuario(@RequestBody Usuario usuario) {
        usuarioServices.guardarUsuario(usuario);
        return ResponseEntity.ok().build();
    }


}
