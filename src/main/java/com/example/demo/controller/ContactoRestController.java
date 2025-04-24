package com.example.demo.controller;

import com.example.demo.service.ContactoService;
import com.example.demo.model.Contacto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // marca la clase como un controlador web
@RequestMapping("/api/contacto")
public class ContactoRestController {

    @Autowired
    ContactoService contactoService;

    @GetMapping("/listarTodos")
    public ResponseEntity<?> listarTodosContactos() {
        List<Contacto> listaTodos = contactoService.listarTodos();
        return ResponseEntity.ok().body(listaTodos);
    }

    @DeleteMapping("/eliminarContacto")
    public ResponseEntity<?> eliminarContacto(@PathVariable("correoContacto") String correo) {
        contactoService.eliminarContacto(correo);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/guardarContacto")
    public ResponseEntity<?> guardarContacto(@RequestBody Contacto contacto) {
        contactoService.guardarContacto(contacto);
        return ResponseEntity.ok().build();
    }


}
