package com.letrasypapeles.backend.service;

import com.letrasypapeles.backend.entity.Inventario;
import com.letrasypapeles.backend.repository.InventarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @InjectMocks
    private InventarioService inventarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodos() {
        List<Inventario> lista = List.of(new Inventario(), new Inventario());
        when(inventarioRepository.findAll()).thenReturn(lista);

        List<Inventario> resultado = inventarioService.obtenerTodos();

        assertEquals(2, resultado.size());
        verify(inventarioRepository).findAll();
    }

    @Test
    void testObtenerPorId() {
        Inventario inv = new Inventario();
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(inv));

        Optional<Inventario> resultado = inventarioService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        verify(inventarioRepository).findById(1L);
    }

    @Test
    void testGuardar() {
        Inventario inv = new Inventario();
        when(inventarioRepository.save(inv)).thenReturn(inv);

        Inventario resultado = inventarioService.guardar(inv);

        assertEquals(inv, resultado);
        verify(inventarioRepository).save(inv);
    }

    @Test
    void testEliminar() {
        inventarioService.eliminar(1L);
        verify(inventarioRepository).deleteById(1L);
    }

    @Test
    void testObtenerPorProductoId() {
        List<Inventario> lista = List.of(new Inventario());
        when(inventarioRepository.findByProductoId(10L)).thenReturn(lista);

        List<Inventario> resultado = inventarioService.obtenerPorProductoId(10L);

        assertEquals(1, resultado.size());
        verify(inventarioRepository).findByProductoId(10L);
    }

    @Test
    void testObtenerPorSucursalId() {
        List<Inventario> lista = List.of(new Inventario());
        when(inventarioRepository.findBySucursalId(5L)).thenReturn(lista);

        List<Inventario> resultado = inventarioService.obtenerPorSucursalId(5L);

        assertEquals(1, resultado.size());
        verify(inventarioRepository).findBySucursalId(5L);
    }

    @Test
    void testObtenerInventarioBajoUmbral() {
        List<Inventario> lista = List.of(new Inventario());
        when(inventarioRepository.findByCantidadLessThan(10)).thenReturn(lista);

        List<Inventario> resultado = inventarioService.obtenerInventarioBajoUmbral(10);

        assertEquals(1, resultado.size());
        verify(inventarioRepository).findByCantidadLessThan(10);
    }
}
