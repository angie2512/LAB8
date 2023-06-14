package com.example.lab8.Repository;

import com.example.lab8.Entity.TipoTicketEvento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoTicketEventoRepository extends JpaRepository<TipoTicketEvento, Integer> {
}