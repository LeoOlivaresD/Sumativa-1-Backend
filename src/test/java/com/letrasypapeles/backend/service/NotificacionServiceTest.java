package com.letrasypapeles.backend.service;

import com.letrasypapeles.backend.entity.Notificacion;
import com.letrasypapeles.backend.repository.NotificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificacionServiceTest {

    @Mock
    private NotificacionRepository notificacionRepository;

    @InjectMocks
    private NotificacionService notificacionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodas() {
        List<Notificacion> lista = List.of(new Notificacion(), new Notificacion());
        when(notificacionRepository.findAll()).thenReturn(lista);

        List<Notificacion> resultado = notificacionService.obtenerTodas();

        assertEquals(2, resultado.size());
        verify(notificacionRepository).findAll();
    }

    @Test
    void testObtenerPorId() {
        Notificacion noti = new Notificacion();
        when(notificacionRepository.findById(1L)).thenReturn(Optional.of(noti));

        Optional<Notificacion> resultado = notificacionService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        verify(notificacionRepository).findById(1L);
    }

    @Test
    void testGuardar() {
        Notificacion noti = new Notificacion();
        when(notificacionRepository.save(noti)).thenReturn(noti);

        Notificacion resultado = notificacionService.guardar(noti);

        assertEquals(noti, resultado);
        verify(notificacionRepository).save(noti);
    }

    @Test
    void testEliminar() {
        notificacionService.eliminar(1L);
        verify(notificacionRepository).deleteById(1L);
    }

    @Test
    void testObtenerPorClienteId() {
        List<Notificacion> lista = List.of(new Notificacion());
        when(notificacionRepository.findByClienteIdCliente(42L)).thenReturn(lista);

        List<Notificacion> resultado = notificacionService.obtenerPorClienteId(42L);

        assertEquals(1, resultado.size());
        verify(notificacionRepository).findByClienteIdCliente(42L);
    }

    @Test
    void testObtenerPorFechaEntre() {
        LocalDateTime inicio = LocalDateTime.now().minusDays(7);
        LocalDateTime fin = LocalDateTime.now();
        List<Notificacion> lista = List.of(new Notificacion());

        when(notificacionRepository.findByFechaBetween(inicio, fin)).thenReturn(lista);

        List<Notificacion> resultado = notificacionService.obtenerPorFechaEntre(inicio, fin);

        assertEquals(1, resultado.size());
        verify(notificacionRepository).findByFechaBetween(inicio, fin);
    }
}
