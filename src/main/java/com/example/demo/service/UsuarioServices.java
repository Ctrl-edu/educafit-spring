package com.example.demo.service;

import com.example.demo.dto.LoginDTO;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServices {

    private final UsuarioRepository usuarioRepository;
    private static final Logger log = LoggerFactory.getLogger(UsuarioServices.class);


    @Autowired
    public UsuarioServices(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> ListarTodos() {
        return usuarioRepository.findAll();
    }

    public void eliminarUsuario(String correo) {
        log.info("Buscando al usuario: {}", correo);
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByCorreo(correo);
        if (usuarioEncontrado.isPresent()) {
            log.info("Se ha encontrado el usuario: {}", usuarioEncontrado.get().getNombre());
            Usuario usuarioAEliminar = usuarioEncontrado.get();
            usuarioRepository.delete(usuarioAEliminar);
            log.info("Se ha eliminado el usuario: {}", correo);

        } else {
            log.warn("No se encontro el usuario en la base de datos");
        }

    }

    //metodo para actualizar
    public Usuario actualizarUsuario(String correo, Usuario usuarioActualizado) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByCorreo(correo);
        if (usuarioExistente.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        // Actualizo los campos necesarios
        Usuario usuario = usuarioExistente.get();
        usuario.setNombre(usuarioActualizado.getNombre());
        usuario.setApellido(usuarioActualizado.getApellido());
        usuario.setGenero(usuarioActualizado.getGenero());
        usuario.setUbicacion(usuarioActualizado.getUbicacion());
        usuario.setObjetivos(usuarioActualizado.getObjetivos());

        return usuarioRepository.save(usuario);
    }


    // este metodo es igual a como si tuviera uno llamado guardarUsuario
    public void registroUsuario(Usuario usuario) throws Exception {
        log.info("Registrando al usuario: {}", usuario.getCorreo());
        List<String> errores = new ArrayList<>();

        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByCorreo(usuario.getCorreo());
        if (usuarioEncontrado.isPresent()) {
            errores.add("Ya existe un usuario con el correo: " + usuario.getCorreo());
        }
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            errores.add("El nombre no puede estar vacío.");
        }
        if (usuario.getApellido() == null || usuario.getApellido().trim().isEmpty()) {
            errores.add("El apellido no puede estar vacío.");
        }
        if (usuario.getGenero() == null || usuario.getGenero().trim().isEmpty()) {
            errores.add("El género no puede estar vacío.");
        }
        if (usuario.getUbicacion() == null || usuario.getUbicacion().trim().isEmpty()) {
            errores.add("La ubicación no puede estar vacía.");
        }
        if (usuario.getObjetivos() == null || usuario.getObjetivos().trim().isEmpty()) {
            errores.add("Los objetivos no pueden estar vacíos.");
        }
        if (usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty()) {
            errores.add("El correo no puede estar vacío.");
        }
        if (usuario.getContraseña() == null || usuario.getContraseña().trim().isEmpty()) {
            errores.add("La contraseña no puede estar vacía.");
        }
        if (usuario.getContraseña().length() < 8) {
            errores.add("La contraseña debe tener mas de 8 caracteres.");
        }

        // Si hay errores, se lanza excepción con todos ellos
        if (!errores.isEmpty()) {
            throw new IllegalArgumentException(String.join("", errores));
        }

        // encriptando la contraseña con la que se registra el usuario
        log.info("Encriptando password..");
        String passwordEncriptada = BCrypt.withDefaults().hashToString(12, usuario.getContraseña().toCharArray());
        log.info("Password encriptada..");

        usuario.setContraseña(passwordEncriptada);

        usuarioRepository.save(usuario);
        log.info("Usuario: {} guardado exitosamente", usuario.getCorreo());

    }

    public Usuario loginUsuario(LoginDTO loginDTO) throws Exception {
        List<String> errores = new ArrayList<>();

        if (loginDTO.getContraseña() == null || loginDTO.getContraseña().trim().isEmpty()) {
            errores.add("La contraseña no puede estar vacía.");
        }

        if (loginDTO.getCorreo() == null || loginDTO.getCorreo().trim().isEmpty()) {
            errores.add("El correo no puede estar vacío.");
        }

        if (!errores.isEmpty()) {
            throw new IllegalArgumentException(String.join("", errores));
        }

        Optional<Usuario> usuarioBuscar = usuarioRepository.findByCorreo(loginDTO.getCorreo());
        if (usuarioBuscar.isEmpty()) {
            throw new Exception("usuario o contraseña incorrectos");
        } else {
            boolean isPassword = BCrypt.verifyer().verify(loginDTO.getContraseña().toCharArray(), usuarioBuscar.get().getContraseña()).verified; //devuelve un true o false dependiendo si es la misma pass o no
            if (isPassword) {
                return usuarioBuscar.get();
            } else {
                return null;
            }
        }
    }

}


