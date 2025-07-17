package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SucursalTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        Sucursal sucursal = new Sucursal(1L, "Sucursal Centro", "Av. Principal 123", "Metropolitana");

        assertThat(sucursal.getId()).isEqualTo(1L);
        assertThat(sucursal.getNombre()).isEqualTo("Sucursal Centro");
        assertThat(sucursal.getDireccion()).isEqualTo("Av. Principal 123");
        assertThat(sucursal.getRegion()).isEqualTo("Metropolitana");
    }

    @Test
    void testBuilder() {
        Sucursal sucursal = Sucursal.builder()
                .id(2L)
                .nombre("Sucursal Norte")
                .direccion("Calle 456")
                .region("Valparaíso")
                .build();

        assertThat(sucursal.getNombre()).isEqualTo("Sucursal Norte");
        assertThat(sucursal.getDireccion()).isEqualTo("Calle 456");
    }

    @Test
    void testSetters() {
        Sucursal sucursal = new Sucursal();
        sucursal.setId(3L);
        sucursal.setNombre("Sucursal Sur");
        sucursal.setRegion("Biobío");

        assertThat(sucursal.getId()).isEqualTo(3L);
        assertThat(sucursal.getNombre()).isEqualTo("Sucursal Sur");
        assertThat(sucursal.getRegion()).isEqualTo("Biobío");
    }

    @Test
    void testEqualsAndHashCode() {
        Sucursal s1 = Sucursal.builder().id(4L).nombre("X").build();
        Sucursal s2 = Sucursal.builder().id(4L).nombre("X").build();

        assertThat(s1).isEqualTo(s2);
        assertThat(s1.hashCode()).isEqualTo(s2.hashCode());
    }

    @Test
    void testToString() {
        Sucursal sucursal = Sucursal.builder()
                .id(5L)
                .nombre("Sucursal Test")
                .direccion("123 Test Ave")
                .region("Ñuble")
                .build();

        assertThat(sucursal.toString()).contains("id=5", "nombre=Sucursal Test", "region=Ñuble");
    }

    @Test
    void testEqualsWithDifferentObjects() {
        Sucursal s1 = Sucursal.builder().id(1L).nombre("X").build();
        Sucursal s2 = Sucursal.builder().id(2L).nombre("Y").build();
        Sucursal s3 = null;
        String otroTipo = "no es sucursal";

        assertThat(s1).isNotEqualTo(s2);         // objetos distintos
        assertThat(s1).isNotEqualTo(s3);         // comparación con null
        assertThat(s1).isNotEqualTo(otroTipo);   // comparación con otro tipo
        assertThat(s1.equals(s1)).isTrue();      // igualdad consigo mismo
    }

    @Test
    void testHashCodeConsistency() {
        Sucursal sucursal = Sucursal.builder()
                .id(7L)
                .nombre("Sucursal Este")
                .region("Los Lagos")
                .build();

        int hash1 = sucursal.hashCode();
        int hash2 = sucursal.hashCode();

        assertThat(hash1).isEqualTo(hash2); // mismo objeto, hash estable
    }






}
