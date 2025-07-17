package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoleEntityTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        RoleEntity role = new RoleEntity();
        role.setId(1L);
        role.setName("ADMIN");

        assertThat(role.getId()).isEqualTo(1L);
        assertThat(role.getName()).isEqualTo("ADMIN");
    }

    @Test
    void testNameConstructor() {
        RoleEntity role = new RoleEntity("USER");

        assertThat(role.getName()).isEqualTo("USER");
        assertThat(role.getId()).isNull(); // no se ha asignado aún
    }

    @Test
    void testEqualsAndHashCode() {
        RoleEntity r1 = new RoleEntity("MOD");
        r1.setId(10L);
        RoleEntity r2 = new RoleEntity("MOD");
        r2.setId(10L);

        assertThat(r1).isEqualTo(r2);
        assertThat(r1.hashCode()).isEqualTo(r2.hashCode());
    }

    @Test
    void testToString() {
        RoleEntity role = new RoleEntity("INVITADO");
        role.setId(99L);

        assertThat(role.toString()).contains("id=99", "name=INVITADO");
    }

    @Test
    void testEqualsWithDifferentScenarios() {
        RoleEntity r1 = new RoleEntity("ADMIN");
        r1.setId(1L);

        RoleEntity r2 = new RoleEntity("USER");
        r2.setId(2L);

        String otroObjeto = "no es rol";

        assertThat(r1).isNotEqualTo(r2);      // diferentes campos
        assertThat(r1).isNotEqualTo(null);    // comparación con null
        assertThat(r1).isNotEqualTo(otroObjeto); // distinto tipo
        assertThat(r1.equals(r1)).isTrue();   // igualdad con sí mismo
    }




}
