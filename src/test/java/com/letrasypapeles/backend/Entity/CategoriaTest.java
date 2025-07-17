package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import com.letrasypapeles.backend.entity.Categoria;

class CategoriaTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        Categoria categoria = new Categoria(1L, "Papelería");
        assertThat(categoria.getId()).isEqualTo(1L);
        assertThat(categoria.getNombre()).isEqualTo("Papelería");
    }

    @Test
    void testSetters() {
        Categoria categoria = new Categoria();
        categoria.setId(2L);
        categoria.setNombre("Oficina");

        assertThat(categoria.getId()).isEqualTo(2L);
        assertThat(categoria.getNombre()).isEqualTo("Oficina");
    }

    @Test
    void testBuilder() {
        Categoria categoria = Categoria.builder()
                .id(3L)
                .nombre("Escolar")
                .build();

        assertThat(categoria.getId()).isEqualTo(3L);
        assertThat(categoria.getNombre()).isEqualTo("Escolar");
    }

    @Test
    void testEqualsAndHashCode() {
        Categoria cat1 = new Categoria(1L, "Arte");
        Categoria cat2 = new Categoria(1L, "Arte");

        assertThat(cat1).isEqualTo(cat2);
        assertThat(cat1.hashCode()).isEqualTo(cat2.hashCode());
    }

    @Test
    void testToString() {
        Categoria categoria = new Categoria(1L, "Tecnología");
        assertThat(categoria.toString()).contains("id=1", "nombre=Tecnología");
    }
}
