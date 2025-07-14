package com.letrasypapeles.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letrasypapeles.backend.config.TestSecurityConfig;
import com.letrasypapeles.backend.entity.Producto;
import com.letrasypapeles.backend.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
@Import(TestSecurityConfig.class)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testObtenerTodos() throws Exception {
        List<Producto> lista = List.of(new Producto(), new Producto());
        when(productoService.obtenerTodos()).thenReturn(lista);

        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testObtenerPorId_Existente() throws Exception {
        Producto producto = new Producto();
        producto.setId(1L);
        when(productoService.obtenerPorId(1L)).thenReturn(Optional.of(producto));

        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testObtenerPorId_NoExistente() throws Exception {
        when(productoService.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrearProducto() throws Exception {
        Producto producto = new Producto();
        producto.setId(1L);
        when(productoService.guardar(any())).thenReturn(producto);

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testActualizarProducto_Existente() throws Exception {
        Producto producto = new Producto();
        producto.setId(1L);
        when(productoService.obtenerPorId(1L)).thenReturn(Optional.of(producto));
        when(productoService.guardar(any())).thenReturn(producto);

        mockMvc.perform(put("/api/productos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isOk());
    }

    @Test
    void testActualizarProducto_NoExistente() throws Exception {
        Producto producto = new Producto();
        when(productoService.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/productos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarProducto_Existente() throws Exception {
        Producto producto = new Producto();
        when(productoService.obtenerPorId(1L)).thenReturn(Optional.of(producto));

        mockMvc.perform(delete("/api/productos/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminarProducto_NoExistente() throws Exception {
        when(productoService.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/productos/1"))
                .andExpect(status().isNotFound());
    }
}
