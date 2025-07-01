package com.letrasypapeles.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letrasypapeles.backend.config.TestSecurityConfig;
import com.letrasypapeles.backend.entity.Sucursal;
import com.letrasypapeles.backend.service.SucursalService;
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

@WebMvcTest(SucursalController.class)
@Import(TestSecurityConfig.class)
class SucursalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SucursalService sucursalService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testObtenerTodas() throws Exception {
        List<Sucursal> lista = List.of(new Sucursal(), new Sucursal());
        when(sucursalService.obtenerTodas()).thenReturn(lista);

        mockMvc.perform(get("/api/sucursales"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testObtenerPorId_Existente() throws Exception {
        Sucursal sucursal = new Sucursal();
        sucursal.setId(1L);
        when(sucursalService.obtenerPorId(1L)).thenReturn(Optional.of(sucursal));

        mockMvc.perform(get("/api/sucursales/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testObtenerPorId_NoExistente() throws Exception {
        when(sucursalService.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/sucursales/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrearSucursal() throws Exception {
        Sucursal sucursal = new Sucursal();
        sucursal.setId(1L);
        when(sucursalService.guardar(any())).thenReturn(sucursal);

        mockMvc.perform(post("/api/sucursales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sucursal)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testActualizarSucursal_Existente() throws Exception {
        Sucursal sucursal = new Sucursal();
        sucursal.setId(1L);
        when(sucursalService.obtenerPorId(1L)).thenReturn(Optional.of(sucursal));
        when(sucursalService.guardar(any())).thenReturn(sucursal);

        mockMvc.perform(put("/api/sucursales/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sucursal)))
                .andExpect(status().isOk());
    }

    @Test
    void testActualizarSucursal_NoExistente() throws Exception {
        Sucursal sucursal = new Sucursal();
        when(sucursalService.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/sucursales/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sucursal)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarSucursal_Existente() throws Exception {
        Sucursal sucursal = new Sucursal();
        when(sucursalService.obtenerPorId(1L)).thenReturn(Optional.of(sucursal));

        mockMvc.perform(delete("/api/sucursales/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminarSucursal_NoExistente() throws Exception {
        when(sucursalService.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/sucursales/1"))
                .andExpect(status().isNotFound());
    }
}
