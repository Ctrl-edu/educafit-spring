package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuario {

    @Id
    @Column
    private Integer idUsuario;

    @Column
    private String nombre;

    @Column
    private String genero;

    @Column
    private String ubicacion;

    @Column
    private String objetivosSalud;

    @Column
    private String preferencias;


}


