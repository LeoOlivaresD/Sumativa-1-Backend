package com.letrasypapeles.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letrasypapeles.backend.config.TestSecurityConfig;
import com.letrasypapeles.backend.entity.RoleEntity;
import com.letrasypapeles.backend.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoleController.class)
@Import(TestSecurityConfig.class)
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @Autowired
    private ObjectMapper objectMapper;

    private RoleEntity role;

    @BeforeEach
    void setUp() {
        role = new RoleEntity();
        role.setId(1L);
        role.setName("ADMIN");
    }

    @Test
    void testObtenerTodos() throws Exception {
        when(roleService.obtenerTodos()).thenReturn(List.of(role));

        mockMvc.perform(get("/api/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("ADMIN"));
    }

    @Test
    void testObtenerPorNombre_Encontrado() throws Exception {
        when(roleService.obtenerPorNombre("ADMIN")).thenReturn(Optional.of(role));

        mockMvc.perform(get("/api/roles/ADMIN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ADMIN"));
    }

    @Test
    void testObtenerPorNombre_NoEncontrado() throws Exception {
        when(roleService.obtenerPorNombre("XYZ")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/roles/XYZ"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrearRole() throws Exception {
        when(roleService.guardar(any())).thenReturn(role);

        mockMvc.perform(post("/api/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(role)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ADMIN"));
    }

    @Test
    void testEliminarRole_Existente() throws Exception {
        when(roleService.obtenerPorNombre("ADMIN")).thenReturn(Optional.of(role));
        doNothing().when(roleService).eliminar("ADMIN");

        mockMvc.perform(delete("/api/roles/ADMIN"))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminarRole_NoExistente() throws Exception {
        when(roleService.obtenerPorNombre("XYZ")).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/roles/XYZ"))
                .andExpect(status().isNotFound());
    }
}
