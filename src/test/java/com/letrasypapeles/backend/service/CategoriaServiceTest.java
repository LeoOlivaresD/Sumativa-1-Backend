package com.letrasypapeles.backend.service;

import com.letrasypapeles.backend.entity.Categoria;
import com.letrasypapeles.backend.repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodas() {
        List<Categoria> lista = List.of(new Categoria(), new Categoria());
        when(categoriaRepository.findAll()).thenReturn(lista);

        List<Categoria> resultado = categoriaService.obtenerTodas();

        assertEquals(2, resultado.size());
        verify(categoriaRepository).findAll();
    }

    @Test
    void testObtenerPorId() {
        Categoria categoria = new Categoria();
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));

        Optional<Categoria> resultado = categoriaService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        verify(categoriaRepository).findById(1L);
    }

    @Test
    void testGuardar() {
        Categoria categoria = new Categoria();
        when(categoriaRepository.save(categoria)).thenReturn(categoria);

        Categoria resultado = categoriaService.guardar(categoria);

        assertEquals(categoria, resultado);
        verify(categoriaRepository).save(categoria);
    }

    @Test
    void testEliminar() {
        categoriaService.eliminar(1L);
        verify(categoriaRepository).deleteById(1L);
    }
}
