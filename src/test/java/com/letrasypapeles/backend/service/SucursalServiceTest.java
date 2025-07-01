package com.letrasypapeles.backend.service;

import com.letrasypapeles.backend.entity.Sucursal;
import com.letrasypapeles.backend.repository.SucursalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SucursalServiceTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @InjectMocks
    private SucursalService sucursalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodas() {
        List<Sucursal> lista = List.of(new Sucursal(), new Sucursal());
        when(sucursalRepository.findAll()).thenReturn(lista);

        List<Sucursal> resultado = sucursalService.obtenerTodas();

        assertEquals(2, resultado.size());
        verify(sucursalRepository).findAll();
    }

    @Test
    void testObtenerPorId() {
        Sucursal sucursal = new Sucursal();
        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursal));

        Optional<Sucursal> resultado = sucursalService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        verify(sucursalRepository).findById(1L);
    }

    @Test
    void testGuardar() {
        Sucursal sucursal = new Sucursal();
        when(sucursalRepository.save(sucursal)).thenReturn(sucursal);

        Sucursal resultado = sucursalService.guardar(sucursal);

        assertEquals(sucursal, resultado);
        verify(sucursalRepository).save(sucursal);
    }

    @Test
    void testEliminar() {
        sucursalService.eliminar(1L);
        verify(sucursalRepository).deleteById(1L);
    }
}
