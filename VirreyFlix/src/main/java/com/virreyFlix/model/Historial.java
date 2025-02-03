package com.virreyFlix.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Historial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    LocalDateTime fechaReproduccion;

    @ManyToOne
    @JoinColumn(name = "perfil_id")
    Perfil perfil;

    @ManyToOne
    @JoinColumn(name = "episodio_id")
    Episodios episodio;

    public Historial() {
        this.fechaReproduccion = LocalDateTime.now();
    }

    public Historial(LocalDateTime fechaReproduccion) {
        this.fechaReproduccion = fechaReproduccion;

    }

    public Episodios getEpisodio() {
        return episodio;
    }

    public void setEpisodio(Episodios episodio) {
        this.episodio = episodio;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getFechaReproduccion() {
        return fechaReproduccion;
    }

    public void setFechaReproduccion(LocalDateTime fechaReproduccion) {
        this.fechaReproduccion = fechaReproduccion;
    }

    @Override
    public String toString() {
        return "Historial{" +
                "id=" + id +
                ", fechaReproduccion=" + fechaReproduccion +
                ", perfil=" + perfil +
                '}';
    }
}
