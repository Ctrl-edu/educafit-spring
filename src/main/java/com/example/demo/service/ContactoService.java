package com.example.demo.service;

import com.example.demo.model.Contacto;
import com.example.demo.repository.ContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service        //indica que es una clase que tiene la logica de negocio
public class ContactoService {

    private final ContactoRepository contactoRepository;

    @Autowired
    public ContactoService(ContactoRepository contactoRepository) {
        this.contactoRepository = contactoRepository;
    }

    public List<Contacto> listarTodos() {
        return contactoRepository.findAll();
    }

    public void guardarContacto(Contacto contacto) {
        contactoRepository.save(contacto);
    }

    public void eliminarContacto(String correo) {
        Optional<Contacto> contactoEncontrado = contactoRepository.findById(correo);
        if (contactoEncontrado.isPresent()) {
            Contacto contactoAEliminar = contactoEncontrado.get();
            contactoRepository.delete(contactoAEliminar);
        } else {
            System.out.println("No se encontro el contacto en la base de datos");
        }

    }


}
