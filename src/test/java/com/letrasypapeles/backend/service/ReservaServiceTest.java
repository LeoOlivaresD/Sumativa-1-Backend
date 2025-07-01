package com.letrasypapeles.backend.service;

import com.letrasypapeles.backend.entity.Reserva;
import com.letrasypapeles.backend.repository.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaService reservaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodas() {
        List<Reserva> lista = List.of(new Reserva(), new Reserva());
        when(reservaRepository.findAll()).thenReturn(lista);

        List<Reserva> resultado = reservaService.obtenerTodas();

        assertEquals(2, resultado.size());
        verify(reservaRepository).findAll();
    }

    @Test
    void testObtenerPorId() {
        Reserva reserva = new Reserva();
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        Optional<Reserva> resultado = reservaService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        verify(reservaRepository).findById(1L);
    }

    @Test
    void testGuardar() {
        Reserva reserva = new Reserva();
        when(reservaRepository.save(reserva)).thenReturn(reserva);

        Reserva resultado = reservaService.guardar(reserva);

        assertEquals(reserva, resultado);
        verify(reservaRepository).save(reserva);
    }

    @Test
    void testEliminar() {
        reservaService.eliminar(1L);
        verify(reservaRepository).deleteById(1L);
    }

    @Test
    void testObtenerPorClienteId() {
        List<Reserva> lista = List.of(new Reserva());
        when(reservaRepository.findByClienteIdCliente(42L)).thenReturn(lista);

        List<Reserva> resultado = reservaService.obtenerPorClienteId(42L);

        assertEquals(1, resultado.size());
        verify(reservaRepository).findByClienteIdCliente(42L);
    }

    @Test
    void testObtenerPorProductoId() {
        List<Reserva> lista = List.of(new Reserva());
        when(reservaRepository.findByProductoId(99L)).thenReturn(lista);

        List<Reserva> resultado = reservaService.obtenerPorProductoId(99L);

        assertEquals(1, resultado.size());
        verify(reservaRepository).findByProductoId(99L);
    }

    @Test
    void testObtenerPorEstado() {
        List<Reserva> lista = List.of(new Reserva());
        when(reservaRepository.findByEstado("PENDIENTE")).thenReturn(lista);

        List<Reserva> resultado = reservaService.obtenerPorEstado("PENDIENTE");

        assertEquals(1, resultado.size());
        verify(reservaRepository).findByEstado("PENDIENTE");
    }
}
