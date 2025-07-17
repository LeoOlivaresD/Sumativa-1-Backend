package com.letrasypapeles.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letrasypapeles.backend.config.TestSecurityConfig;
import com.letrasypapeles.backend.entity.Producto;
import com.letrasypapeles.backend.hateoas.ProductoModelAssembler;
import com.letrasypapeles.backend.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductoController.class)
@Import({TestSecurityConfig.class, ProductoModelAssembler.class})
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @MockBean
    private ProductoModelAssembler assembler;

    @Autowired
    private ObjectMapper objectMapper;

    private Producto producto;

    @BeforeEach
    void setup() {
        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Test");
        producto.setDescripcion("Desc");
        producto.setPrecio(new BigDecimal("100.00"));
    }

    @Test
    void testObtenerTodos() throws Exception {
        when(productoService.obtenerTodos()).thenReturn(List.of(producto));
        when(assembler.toModel(producto)).thenReturn(EntityModel.of(producto));

        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk());
    }

    @Test
    void testObtenerPorId_Existente() throws Exception {
        when(productoService.obtenerPorId(1L)).thenReturn(Optional.of(producto));
        when(assembler.toModel(producto)).thenReturn(EntityModel.of(producto));

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
        when(productoService.guardar(any())).thenReturn(producto);

        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isOk());
    }

    @Test
    void testActualizarProducto_Existente() throws Exception {
        when(productoService.obtenerPorId(1L)).thenReturn(Optional.of(producto));
        when(productoService.guardar(any())).thenReturn(producto);

        mockMvc.perform(put("/api/productos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isOk());
    }

    @Test
    void testActualizarProducto_NoExistente() throws Exception {
        when(productoService.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/productos/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarProducto_Existente() throws Exception {
        when(productoService.obtenerPorId(1L)).thenReturn(Optional.of(producto));
        doNothing().when(productoService).eliminar(1L);

        mockMvc.perform(delete("/api/productos/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminarProducto_NoExistente() throws Exception {
        when(productoService.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/productos/99"))
                .andExpect(status().isNotFound());
    }
}
