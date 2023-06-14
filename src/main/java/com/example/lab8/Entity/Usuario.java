package com.example.lab8.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Usuario implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idusuario")
    private int idusuario;
    @Basic
    @Column(name = "dni")
    private String dni;
    @Basic
    @Column(name = "correo")
    private String correo;
    @Basic
    @Column(name = "nombres")
    private String nombres;
    @Basic
    @JoinColumn(name = "apellidos")
    private String apellidos;
}
