package com.letrasypapeles.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letrasypapeles.backend.config.TestSecurityConfig;
import com.letrasypapeles.backend.entity.Categoria;
import com.letrasypapeles.backend.service.CategoriaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoriaController.class)
@Import(TestSecurityConfig.class)
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoriaService categoriaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testObtenerTodas() throws Exception {
        List<Categoria> lista = List.of(new Categoria(), new Categoria());
        when(categoriaService.obtenerTodas()).thenReturn(lista);

        mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testObtenerPorId_Existente() throws Exception {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        when(categoriaService.obtenerPorId(1L)).thenReturn(Optional.of(categoria));

        mockMvc.perform(get("/api/categorias/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testObtenerPorId_NoExistente() throws Exception {
        when(categoriaService.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/categorias/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrearCategoria() throws Exception {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        when(categoriaService.guardar(any())).thenReturn(categoria);

        mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoria)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testActualizarCategoria_Existente() throws Exception {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        when(categoriaService.obtenerPorId(1L)).thenReturn(Optional.of(categoria));
        when(categoriaService.guardar(any())).thenReturn(categoria);

        mockMvc.perform(put("/api/categorias/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoria)))
                .andExpect(status().isOk());
    }

    @Test
    void testActualizarCategoria_NoExistente() throws Exception {
        Categoria categoria = new Categoria();
        when(categoriaService.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/categorias/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoria)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarCategoria_Existente() throws Exception {
        Categoria categoria = new Categoria();
        when(categoriaService.obtenerPorId(1L)).thenReturn(Optional.of(categoria));

        mockMvc.perform(delete("/api/categorias/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminarCategoria_NoExistente() throws Exception {
        when(categoriaService.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/categorias/1"))
                .andExpect(status().isNotFound());
    }
}
