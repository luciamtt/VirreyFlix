package com.virreyFlix.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Serie")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String titulo;

    @ManyToOne
    @JoinColumn(name = "genero")
    private Genero genero;

    @Column(name = "edadMinima")
    private int calificacionEdad;


    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
    private List<Episodio> episodios;

    public Serie() {

    }

    public Serie(int calificacionEdad, Genero genero, List<Episodio> episodios, String titulo) {
        this.calificacionEdad = calificacionEdad;
        this.genero = genero;
        this.episodios = episodios;
        this.titulo = titulo;
    }



    public int getCalificacionEdad() {
        return calificacionEdad;
    }

    public void setCalificacionEdad(int calificacionEdad) {
        this.calificacionEdad = calificacionEdad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        this.episodios = episodios;
    }

    @Override
    public String toString() {
        return "Serie{" +
                "calificacionEdad=" + calificacionEdad +
                ", id=" + id +
                ", titulo='" + titulo + '\'' +
                ", genero=" + genero +
                ", episodios=" + episodios +
                '}';
    }
}