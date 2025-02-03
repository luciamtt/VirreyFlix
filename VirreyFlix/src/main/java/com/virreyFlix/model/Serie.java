package com.virreyFlix.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String nombre;
    @Column(length = 100)
    String genero;
    @Column(length = 100, unique = true)
    int clasificacion;

    public Serie(String nombre, String genero, int clasificacion) {
        this.nombre = nombre;
        this.genero = genero;
        this.clasificacion = clasificacion;
    }

    Set<Episodios> episodios = new HashSet<>();

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(int clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "Serie{" +
                "nombre='" + nombre + '\'' +
                ", genero='" + genero + '\'' +
                ", clasificacion=" + clasificacion +
                ", episodios=" + episodios +
                '}';
    }
}