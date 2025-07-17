package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProveedorTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        Proveedor proveedor = new Proveedor(1L, "Papelería Global", "contacto@pg.com");

        assertThat(proveedor.getId()).isEqualTo(1L);
        assertThat(proveedor.getNombre()).isEqualTo("Papelería Global");
        assertThat(proveedor.getContacto()).isEqualTo("contacto@pg.com");
    }

    @Test
    void testBuilder() {
        Proveedor proveedor = Proveedor.builder()
                .id(2L)
                .nombre("OfiCorp")
                .contacto("ventas@oficorp.cl")
                .build();

        assertThat(proveedor.getNombre()).isEqualTo("OfiCorp");
        assertThat(proveedor.getContacto()).contains("ventas");
    }

    @Test
    void testSetters() {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(3L);
        proveedor.setNombre("Distribuidora Chile");
        proveedor.setContacto("dc@proveedores.cl");

        assertThat(proveedor.getId()).isEqualTo(3L);
        assertThat(proveedor.getNombre()).isEqualTo("Distribuidora Chile");
    }

    @Test
    void testEqualsAndHashCode() {
        Proveedor p1 = Proveedor.builder().id(10L).nombre("X").build();
        Proveedor p2 = Proveedor.builder().id(10L).nombre("X").build();

        assertThat(p1).isEqualTo(p2);
        assertThat(p1.hashCode()).isEqualTo(p2.hashCode());
    }

    @Test
    void testToString() {
        Proveedor proveedor = Proveedor.builder()
                .id(4L)
                .nombre("ProveedorTest")
                .contacto("test@test.com")
                .build();

        assertThat(proveedor.toString()).contains("id=4", "nombre=ProveedorTest", "contacto=test@test.com");
    }
}
