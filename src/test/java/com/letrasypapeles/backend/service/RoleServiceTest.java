package com.letrasypapeles.backend.service;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.letrasypapeles.backend.entity.RoleEntity;
import com.letrasypapeles.backend.repository.RoleRepository;

class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodos() {
        List<RoleEntity> roles = List.of(new RoleEntity("ADMIN"), new RoleEntity("CLIENTE"));
        when(roleRepository.findAll()).thenReturn(roles);

        List<RoleEntity> resultado = roleService.obtenerTodos();

        assertEquals(2, resultado.size());
        verify(roleRepository).findAll();
    }

    @Test
    void testObtenerPorNombre() {
        RoleEntity rol = new RoleEntity("ADMIN");
        when(roleRepository.findByName("ADMIN")).thenReturn(Optional.of(rol));

        Optional<RoleEntity> resultado = roleService.obtenerPorNombre("ADMIN");

        assertTrue(resultado.isPresent());
        assertEquals("ADMIN", resultado.get().getName());
    }

    @Test
    void testGuardar() {
        RoleEntity rol = new RoleEntity("EMPLEADO");
        when(roleRepository.save(rol)).thenReturn(rol);

        RoleEntity resultado = roleService.guardar(rol);

        assertEquals("EMPLEADO", resultado.getName());
        verify(roleRepository).save(rol);
    }

    @Test
    void testEliminar() {
        doNothing().when(roleRepository).deleteByName("ADMIN");

        roleService.eliminar("ADMIN");

        verify(roleRepository).deleteByName("ADMIN");
    }
}
