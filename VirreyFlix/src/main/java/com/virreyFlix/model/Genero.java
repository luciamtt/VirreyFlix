package com.virreyFlix.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Genero {

    @Id
    @GeneratedValue
    int id;
    String nombre;
}
