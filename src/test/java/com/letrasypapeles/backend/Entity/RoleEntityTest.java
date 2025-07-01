package com.letrasypapeles.backend.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import com.letrasypapeles.backend.entity.RoleEntity;

class RoleEntityTest {
    //correcion
    @Test
    void testConstructorVacio() {
        RoleEntity role = new RoleEntity();
        assertNotNull(role);
    }

    @Test
    void testConstructorConParametro() {
        RoleEntity role = new RoleEntity("EMPLEADO");
        assertNotNull(role);
    }

    @Test
    void testSettersYGetters() {
        RoleEntity role = new RoleEntity();
        role.setId(1L);
        role.setName("ADMIN");

        assertEquals(1L, role.getId());
        assertEquals("ADMIN", role.getName());
    }
}
