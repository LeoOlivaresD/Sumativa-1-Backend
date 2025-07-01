package com.letrasypapeles.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.letrasypapeles.backend.config.TestSecurityConfig;

@WebMvcTest(GerenteController.class)
@Import(TestSecurityConfig.class)
class GerenteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testAccesoGerente() throws Exception {
        mockMvc.perform(get("/gerente"))
                .andExpect(status().isOk())
                .andExpect(content().string("Acceso solo a Gerente"));
    }
}
