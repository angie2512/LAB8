package com.example.lab8.Repository;

import com.example.lab8.Entity.Evento;
import com.example.lab8.Entity.TipoTicketEvento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
    List<TipoTicketEvento> findTiposTicketEventoByEvento(Evento evento);

}