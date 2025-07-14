package com.letrasypapeles.backend.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.letrasypapeles.backend.config.TestSecurityConfig;
import com.letrasypapeles.backend.entity.Cliente;
import com.letrasypapeles.backend.service.ClienteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
@Import(TestSecurityConfig.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = Cliente.builder()
                .idCliente(1L)
                .nombre("Juan")
                .email("juan@mail.com")
                .contraseña("123")
                .puntosFidelidad(100)
                .build();
    }

    @Test
void testCliente() throws Exception {
    mockMvc.perform(get("/api/clientes/perfil"))
            .andExpect(status().isOk())
            .andExpect(content().string("Eres el cliente"));
}

@Test
void testObtenerTodos() throws Exception {
    when(clienteService.obtenerTodos()).thenReturn(List.of(cliente));

    mockMvc.perform(get("/api/clientes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nombre").value("Juan"));
}

@Test
void testObtenerPorIdEncontrado() throws Exception {
    when(clienteService.obtenerPorId(1L)).thenReturn(Optional.of(cliente));

    mockMvc.perform(get("/api/clientes/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value("juan@mail.com"));
}

@Test
void testObtenerPorIdNoEncontrado() throws Exception {
    when(clienteService.obtenerPorId(99L)).thenReturn(Optional.empty());

    mockMvc.perform(get("/api/clientes/99"))
            .andExpect(status().isNotFound());
}

@Test
void testRegistrarCliente() throws Exception {
    when(clienteService.registrarCliente(any())).thenReturn(cliente);

    mockMvc.perform(post("/api/clientes/registro")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(cliente)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Juan"));
}

@Test
void testActualizarClienteEncontrado() throws Exception {
    when(clienteService.obtenerPorId(1L)).thenReturn(Optional.of(cliente));
    when(clienteService.actualizarCliente(any())).thenReturn(cliente);

    mockMvc.perform(put("/api/clientes/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(cliente)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value("juan@mail.com"));
}

@Test
void testActualizarClienteNoEncontrado() throws Exception {
    when(clienteService.obtenerPorId(99L)).thenReturn(Optional.empty());

    mockMvc.perform(put("/api/clientes/99")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(cliente)))
            .andExpect(status().isNotFound());
}

@Test
void testEliminarClienteEncontrado() throws Exception {
    when(clienteService.obtenerPorId(1L)).thenReturn(Optional.of(cliente));
    Mockito.doNothing().when(clienteService).eliminar(1L);

    mockMvc.perform(delete("/api/clientes/1"))
            .andExpect(status().isOk());
}

@Test
void testEliminarClienteNoEncontrado() throws Exception {
    when(clienteService.obtenerPorId(99L)).thenReturn(Optional.empty());

    mockMvc.perform(delete("/api/clientes/99"))
            .andExpect(status().isNotFound());
}
}