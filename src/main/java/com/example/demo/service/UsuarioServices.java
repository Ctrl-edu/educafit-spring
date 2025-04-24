package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServices {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServices(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> ListarTodos() {
        return usuarioRepository.findAll();
    }

    public void guardarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(Integer idUsuario) {
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(idUsuario);
        if (usuarioEncontrado.isPresent()) {
            Usuario usuarioAEliminar = usuarioEncontrado.get();
            usuarioRepository.delete(usuarioAEliminar);
        } else {
            System.out.println("No se encontro el usuario en la base de datos");
        }

    }

}
