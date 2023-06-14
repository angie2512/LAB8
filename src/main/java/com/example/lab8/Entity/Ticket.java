package com.example.lab8.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Ticket implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "idTipoTicket")
    private TipoTicketEvento idTipoTicket;
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario idUsuario;

}
