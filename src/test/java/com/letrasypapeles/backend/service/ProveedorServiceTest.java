package com.letrasypapeles.backend.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.letrasypapeles.backend.entity.Proveedor;
import com.letrasypapeles.backend.repository.ProveedorRepository;

class ProveedorServiceTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProveedorService proveedorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodos() {
        List<Proveedor> lista = List.of(new Proveedor(), new Proveedor());
        when(proveedorRepository.findAll()).thenReturn(lista);

        List<Proveedor> resultado = proveedorService.obtenerTodos();

        assertEquals(2, resultado.size());
        verify(proveedorRepository).findAll();
    }

    @Test
    void testObtenerPorId() {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(1L);

        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(proveedor));

        Optional<Proveedor> resultado = proveedorService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
    }

    @Test
    void testGuardarProveedor() {
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre("Proveedor X");

        when(proveedorRepository.save(proveedor)).thenReturn(proveedor);

        Proveedor resultado = proveedorService.guardar(proveedor);

        assertEquals("Proveedor X", resultado.getNombre());
        verify(proveedorRepository).save(proveedor);
    }

    @Test
    void testEliminarProveedor() {
        doNothing().when(proveedorRepository).deleteById(1L);

        proveedorService.eliminar(1L);

        verify(proveedorRepository).deleteById(1L);
    }
}
