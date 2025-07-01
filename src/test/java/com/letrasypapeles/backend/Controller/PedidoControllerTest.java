package com.letrasypapeles.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letrasypapeles.backend.config.TestSecurityConfig;
import com.letrasypapeles.backend.entity.Pedido;
import com.letrasypapeles.backend.service.PedidoService;
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

@WebMvcTest(PedidoController.class)
@Import(TestSecurityConfig.class)
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testObtenerTodos() throws Exception {
        List<Pedido> lista = List.of(new Pedido(), new Pedido());
        when(pedidoService.obtenerTodos()).thenReturn(lista);

        mockMvc.perform(get("/api/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testObtenerPorId_Existente() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        when(pedidoService.obtenerPorId(1L)).thenReturn(Optional.of(pedido));

        mockMvc.perform(get("/api/pedidos/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testObtenerPorId_NoExistente() throws Exception {
        when(pedidoService.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/pedidos/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testObtenerPorClienteId() throws Exception {
        when(pedidoService.obtenerPorClienteId(42L)).thenReturn(List.of(new Pedido()));

        mockMvc.perform(get("/api/pedidos/cliente/42"))
                .andExpect(status().isOk());
    }

    @Test
    void testCrearPedido() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        when(pedidoService.guardar(any())).thenReturn(pedido);

        mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testActualizarPedido_Existente() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        when(pedidoService.obtenerPorId(1L)).thenReturn(Optional.of(pedido));
        when(pedidoService.guardar(any())).thenReturn(pedido);

        mockMvc.perform(put("/api/pedidos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isOk());
    }

    @Test
    void testActualizarPedido_NoExistente() throws Exception {
        Pedido pedido = new Pedido();
        when(pedidoService.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/pedidos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarPedido_Existente() throws Exception {
        Pedido pedido = new Pedido();
        when(pedidoService.obtenerPorId(1L)).thenReturn(Optional.of(pedido));

        mockMvc.perform(delete("/api/pedidos/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminarPedido_NoExistente() throws Exception {
        when(pedidoService.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/pedidos/1"))
                .andExpect(status().isNotFound());
    }
}
