package com.example.demo.service;

import com.example.demo.dto.LoginDTO;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import at.favre.lib.crypto.bcrypt.BCrypt;


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

    public void eliminarUsuario(Integer idUsuario) {
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(idUsuario);
        if (usuarioEncontrado.isPresent()) {
            Usuario usuarioAEliminar = usuarioEncontrado.get();
            usuarioRepository.delete(usuarioAEliminar);
        } else {
            System.out.println("No se encontro el usuario en la base de datos");
        }

    }

    // este metodo es igual a como si tuviera uno llamado guardarUsuario
    public void registroUsuario(Usuario usuario) throws Exception {
        Usuario usuarioEncontrado = usuarioRepository.findByCorreo(usuario.getCorreo());
        if (usuarioEncontrado != null) {
            throw new Exception("Ya existe un usuario con el correo: " + usuario.getCorreo());
        }
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (usuario.getApellido() == null || usuario.getApellido().trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío.");
        }
        if (usuario.getGenero() == null || usuario.getGenero().trim().isEmpty()) {
            throw new IllegalArgumentException("El género no puede estar vacío.");
        }
        if (usuario.getUbicacion() == null || usuario.getUbicacion().trim().isEmpty()) {
            throw new IllegalArgumentException("La ubicación no puede estar vacía.");
        }
        if (usuario.getObjetivos_salud() == null || usuario.getObjetivos_salud().trim().isEmpty()) {
            throw new IllegalArgumentException("Los objetivos de salud no estar vacíos.");
        }
        if (usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty()) {
            throw new IllegalArgumentException("El correo no puede estar vacío.");
        }
        if (usuario.getContraseña() == null || usuario.getContraseña().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        }

        // encriptando la contraseña con la que se registra el usuario
        String passwordEncriptada = BCrypt.withDefaults().hashToString(12, usuario.getContraseña().toCharArray());
        usuario.setContraseña(passwordEncriptada);
        usuarioRepository.save(usuario);

    }

    public boolean loginUsuario(LoginDTO loginDTO) throws Exception {
        if (loginDTO.getContraseña() == null || loginDTO.getContraseña().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        }

        if (loginDTO.getCorreo() == null || loginDTO.getCorreo().trim().isEmpty()) {
            throw new IllegalArgumentException("El correo no puede estar vacío.");
        }

        Usuario usuarioABuscar = usuarioRepository.findByCorreo(loginDTO.getCorreo());
        if (usuarioABuscar == null) {
            throw new Exception("No existe un usuario registrado con el email: " + loginDTO.getCorreo());
        } else {
            boolean isPassword = BCrypt.verifyer().verify(loginDTO.getContraseña().toCharArray(), usuarioABuscar.getContraseña()).verified;
            if (isPassword) {
                return isPassword;
            } else {
                return false;
            }
        }
    }

}


