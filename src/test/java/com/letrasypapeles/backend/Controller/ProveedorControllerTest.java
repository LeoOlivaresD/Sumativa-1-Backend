package com.letrasypapeles.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letrasypapeles.backend.config.TestSecurityConfig;
import com.letrasypapeles.backend.entity.Proveedor;
import com.letrasypapeles.backend.service.ProveedorService;
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

@WebMvcTest(ProveedorController.class)
@Import(TestSecurityConfig.class)
class ProveedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProveedorService proveedorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testObtenerTodos() throws Exception {
        List<Proveedor> lista = List.of(new Proveedor(), new Proveedor());
        when(proveedorService.obtenerTodos()).thenReturn(lista);

        mockMvc.perform(get("/api/proveedores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testObtenerPorId_Existente() throws Exception {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(1L);
        when(proveedorService.obtenerPorId(1L)).thenReturn(Optional.of(proveedor));

        mockMvc.perform(get("/api/proveedores/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testObtenerPorId_NoExistente() throws Exception {
        when(proveedorService.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/proveedores/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrearProveedor() throws Exception {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(1L);
        when(proveedorService.guardar(any())).thenReturn(proveedor);

        mockMvc.perform(post("/api/proveedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proveedor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testActualizarProveedor_Existente() throws Exception {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(1L);
        when(proveedorService.obtenerPorId(1L)).thenReturn(Optional.of(proveedor));
        when(proveedorService.guardar(any())).thenReturn(proveedor);

        mockMvc.perform(put("/api/proveedores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proveedor)))
                .andExpect(status().isOk());
    }

    @Test
    void testActualizarProveedor_NoExistente() throws Exception {
        Proveedor proveedor = new Proveedor();
        when(proveedorService.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/proveedores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proveedor)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarProveedor_Existente() throws Exception {
        Proveedor proveedor = new Proveedor();
        when(proveedorService.obtenerPorId(1L)).thenReturn(Optional.of(proveedor));

        mockMvc.perform(delete("/api/proveedores/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminarProveedor_NoExistente() throws Exception {
        when(proveedorService.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/proveedores/1"))
                .andExpect(status().isNotFound());
    }
}
