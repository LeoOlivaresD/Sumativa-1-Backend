package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RoleEntityTest {

    @Test
    void testCustomConstructor() {
        RoleEntity role = new RoleEntity("ADMIN");
        assertEquals("ADMIN", role.getName());
    }

    @Test
    void testAllArgsConstructor() {
        RoleEntity role = new RoleEntity(1L, "USER");
        assertEquals(1L, role.getId());
        assertEquals("USER", role.getName());
    }

    @Test
    void testBuilder() {
        RoleEntity role = RoleEntity.builder()
                .id(2L)
                .name("TEST")
                .build();
        assertEquals(2L, role.getId());
        assertEquals("TEST", role.getName());
    }

    @Test
    void testSettersAndGetters() {
        RoleEntity role = new RoleEntity();
        role.setId(3L);
        role.setName("GUEST");
        assertEquals(3L, role.getId());
        assertEquals("GUEST", role.getName());
    }
}
