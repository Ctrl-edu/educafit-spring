package com.example.demo.model;


import jakarta.persistence.*;

@Entity                         // esto indica que esta clase se va a mapear a una tabla
@Table(name = "contactanos")  // aqui agrego el nombre de la tabla como esta en la base de datos
public class Contacto {      //los nombres de las clases no deben ser en plural por buena practica ya que representa 1 sola instancia

    @Column
    private String nombres;

    @Id                 // indicador de primary key
    @Column             // se pueden espeficar diferentes parametros como el nombre de la columna, que no sea null y la longitud
    private String correo;

    @Column
    private String telefono;

    @Column
    private String mensaje;


    // los getters and setters es recomendable que siempre esten explicitos (o usar @Getter y @Setter de Lombok)
    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
