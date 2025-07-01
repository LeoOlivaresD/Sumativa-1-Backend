package com.letrasypapeles.backend.service;

import com.letrasypapeles.backend.entity.Pedido;
import com.letrasypapeles.backend.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodos() {
        List<Pedido> lista = List.of(new Pedido(), new Pedido());
        when(pedidoRepository.findAll()).thenReturn(lista);

        List<Pedido> resultado = pedidoService.obtenerTodos();

        assertEquals(2, resultado.size());
        verify(pedidoRepository).findAll();
    }

    @Test
    void testObtenerPorId() {
        Pedido pedido = new Pedido();
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        Optional<Pedido> resultado = pedidoService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        verify(pedidoRepository).findById(1L);
    }

    @Test
    void testGuardar() {
        Pedido pedido = new Pedido();
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        Pedido resultado = pedidoService.guardar(pedido);

        assertEquals(pedido, resultado);
        verify(pedidoRepository).save(pedido);
    }

    @Test
    void testEliminar() {
        pedidoService.eliminar(1L);
        verify(pedidoRepository).deleteById(1L);
    }

    @Test
    void testObtenerPorClienteId() {
        List<Pedido> lista = List.of(new Pedido());
        when(pedidoRepository.findByClienteIdCliente(42L)).thenReturn(lista);

        List<Pedido> resultado = pedidoService.obtenerPorClienteId(42L);

        assertEquals(1, resultado.size());
        verify(pedidoRepository).findByClienteIdCliente(42L);
    }

    @Test
    void testObtenerPorEstado() {
        List<Pedido> lista = List.of(new Pedido());
        when(pedidoRepository.findByEstado("ENTREGADO")).thenReturn(lista);

        List<Pedido> resultado = pedidoService.obtenerPorEstado("ENTREGADO");

        assertEquals(1, resultado.size());
        verify(pedidoRepository).findByEstado("ENTREGADO");
    }
}
