package com.letrasypapeles.backend.controller;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letrasypapeles.backend.config.TestSecurityConfig;
import com.letrasypapeles.backend.entity.Inventario;
import com.letrasypapeles.backend.service.InventarioService;


@Import(TestSecurityConfig.class)
@WebMvcTest(InventarioController.class)
class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InventarioService inventarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testObtenerTodos() throws Exception {
        List<Inventario> lista = List.of(new Inventario(), new Inventario());
        when(inventarioService.obtenerTodos()).thenReturn(lista);

        mockMvc.perform(get("/api/inventarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testObtenerPorId_Existente() throws Exception {
        Inventario inv = new Inventario();
        inv.setId(1L);
        when(inventarioService.obtenerPorId(1L)).thenReturn(Optional.of(inv));

        mockMvc.perform(get("/api/inventarios/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testObtenerPorId_NoExistente() throws Exception {
        when(inventarioService.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/inventarios/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testObtenerPorProductoId() throws Exception {
        when(inventarioService.obtenerPorProductoId(10L)).thenReturn(List.of(new Inventario()));

        mockMvc.perform(get("/api/inventarios/producto/10"))
                .andExpect(status().isOk());
    }

    @Test
    void testObtenerPorSucursalId() throws Exception {
        when(inventarioService.obtenerPorSucursalId(5L)).thenReturn(List.of(new Inventario()));

        mockMvc.perform(get("/api/inventarios/sucursal/5"))
                .andExpect(status().isOk());
    }

    @Test
    void testCrearInventario() throws Exception {
        Inventario inv = new Inventario();
        inv.setId(1L);
        when(inventarioService.guardar(any())).thenReturn(inv);

        mockMvc.perform(post("/api/inventarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inv)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testActualizarInventario_Existente() throws Exception {
        Inventario inv = new Inventario();
        inv.setId(1L);
        when(inventarioService.obtenerPorId(1L)).thenReturn(Optional.of(inv));
        when(inventarioService.guardar(any())).thenReturn(inv);

        mockMvc.perform(put("/api/inventarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inv)))
                .andExpect(status().isOk());
    }

    @Test
    void testActualizarInventario_NoExistente() throws Exception {
        Inventario inv = new Inventario();
        when(inventarioService.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/inventarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inv)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarInventario_Existente() throws Exception {
        Inventario inv = new Inventario();
        when(inventarioService.obtenerPorId(1L)).thenReturn(Optional.of(inv));

        mockMvc.perform(delete("/api/inventarios/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminarInventario_NoExistente() throws Exception {
        when(inventarioService.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/inventarios/1"))
                .andExpect(status().isNotFound());
    }
}
