package com.example.demo.repository;

import com.example.demo.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository         // indica que es una interface para operaciones en la base de datos
public interface ContactoRepository extends JpaRepository<Contacto, String> {

}
