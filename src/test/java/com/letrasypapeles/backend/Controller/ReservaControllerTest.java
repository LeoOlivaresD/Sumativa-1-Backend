package com.letrasypapeles.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letrasypapeles.backend.config.TestSecurityConfig;
import com.letrasypapeles.backend.entity.Reserva;
import com.letrasypapeles.backend.service.ReservaService;
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

@WebMvcTest(ReservaController.class)
@Import(TestSecurityConfig.class)
class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservaService reservaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testObtenerTodas() throws Exception {
        List<Reserva> lista = List.of(new Reserva(), new Reserva());
        when(reservaService.obtenerTodas()).thenReturn(lista);

        mockMvc.perform(get("/api/reservas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testObtenerPorId_Existente() throws Exception {
        Reserva reserva = new Reserva();
        reserva.setId(1L);
        when(reservaService.obtenerPorId(1L)).thenReturn(Optional.of(reserva));

        mockMvc.perform(get("/api/reservas/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testObtenerPorId_NoExistente() throws Exception {
        when(reservaService.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/reservas/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testObtenerPorClienteId() throws Exception {
        when(reservaService.obtenerPorClienteId(42L)).thenReturn(List.of(new Reserva()));

        mockMvc.perform(get("/api/reservas/cliente/42"))
                .andExpect(status().isOk());
    }

    @Test
    void testCrearReserva() throws Exception {
        Reserva reserva = new Reserva();
        reserva.setId(1L);
        when(reservaService.guardar(any())).thenReturn(reserva);

        mockMvc.perform(post("/api/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reserva)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testActualizarReserva_Existente() throws Exception {
        Reserva reserva = new Reserva();
        reserva.setId(1L);
        when(reservaService.obtenerPorId(1L)).thenReturn(Optional.of(reserva));
        when(reservaService.guardar(any())).thenReturn(reserva);

        mockMvc.perform(put("/api/reservas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reserva)))
                .andExpect(status().isOk());
    }

    @Test
    void testActualizarReserva_NoExistente() throws Exception {
        Reserva reserva = new Reserva();
        when(reservaService.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/reservas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reserva)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarReserva_Existente() throws Exception {
        Reserva reserva = new Reserva();
        when(reservaService.obtenerPorId(1L)).thenReturn(Optional.of(reserva));

        mockMvc.perform(delete("/api/reservas/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminarReserva_NoExistente() throws Exception {
        when(reservaService.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/reservas/1"))
                .andExpect(status().isNotFound());
    }
}
