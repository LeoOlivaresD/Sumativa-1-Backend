package com.letrasypapeles.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letrasypapeles.backend.config.TestSecurityConfig;
import com.letrasypapeles.backend.entity.Notificacion;
import com.letrasypapeles.backend.service.NotificacionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificacionController.class)
@Import(TestSecurityConfig.class)
class NotificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotificacionService notificacionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testObtenerTodas() throws Exception {
        List<Notificacion> lista = List.of(new Notificacion(), new Notificacion());
        when(notificacionService.obtenerTodas()).thenReturn(lista);

        mockMvc.perform(get("/api/notificaciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testObtenerPorId_Existente() throws Exception {
        Notificacion notificacion = new Notificacion();
        notificacion.setId(1L);
        when(notificacionService.obtenerPorId(1L)).thenReturn(Optional.of(notificacion));

        mockMvc.perform(get("/api/notificaciones/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testObtenerPorId_NoExistente() throws Exception {
        when(notificacionService.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/notificaciones/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testObtenerPorClienteId() throws Exception {
        when(notificacionService.obtenerPorClienteId(42L)).thenReturn(List.of(new Notificacion()));

        mockMvc.perform(get("/api/notificaciones/cliente/42"))
                .andExpect(status().isOk());
    }

    @Test
    void testCrearNotificacion() throws Exception {
        Notificacion notificacion = new Notificacion();
        notificacion.setId(1L);
        notificacion.setFecha(LocalDateTime.now());
        when(notificacionService.guardar(any())).thenReturn(notificacion);

        mockMvc.perform(post("/api/notificaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notificacion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testEliminarNotificacion_Existente() throws Exception {
        Notificacion notificacion = new Notificacion();
        when(notificacionService.obtenerPorId(1L)).thenReturn(Optional.of(notificacion));

        mockMvc.perform(delete("/api/notificaciones/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminarNotificacion_NoExistente() throws Exception {
        when(notificacionService.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/notificaciones/1"))
                .andExpect(status().isNotFound());
    }
}
