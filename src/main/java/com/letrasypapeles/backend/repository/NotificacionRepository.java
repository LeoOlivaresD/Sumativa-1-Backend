package com.letrasypapeles.backend.repository;

import com.letrasypapeles.backend.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    List<Notificacion> findByClienteIdCliente(Long idCliente);

    List<Notificacion> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}
