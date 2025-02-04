package com.virreyFlix.model;

import jakarta.persistence.*;

@Entity
@Table(name = "genero")
public class Genero {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(nullable = false, unique = true)
        private String nombre;

        public Genero() {
        }

        public Genero(String nombre) {
            this.nombre = nombre;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public String toString() {
            return "Genero{" +
                    "id=" + id +
                    ", nombre='" + nombre + '\'' +
                    '}';
        }
    }


