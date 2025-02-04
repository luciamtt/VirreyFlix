package com.virreyFlix.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(length = 100)
    String nombre;

    int edad;

    @OneToOne
    Usuario u;

    public Perfil(String nombre, Usuario usuario) {
    }

    public Perfil(String nombre, Usuario u, int edad) {
        this.nombre = nombre;
        this.u = u;
        this.edad = edad;
    }
    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true)
    private Usuario usuario;

    @OneToMany(mappedBy = "perfil")
    Set<Historial> historalies = new HashSet<>();

    public Usuario getU() {
        return u;
    }

    public void setU(Usuario u) {
        this.u = u;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Perfil{" +
                "edad=" + edad +
                ", nombre='" + nombre + '\'' +
                ", id=" + id +
                '}';
    }
}


