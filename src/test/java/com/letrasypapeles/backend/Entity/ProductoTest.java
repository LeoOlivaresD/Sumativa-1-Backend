package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductoTest {

    @Test
    void testBuilderFullCoverage() {
        Categoria categoria = Categoria.builder().id(1L).nombre("Oficina").build();
        Proveedor proveedor = Proveedor.builder().id(1L).nombre("ACME").contacto("acme@correo.com").build();

        Producto producto = Producto.builder()
                .id(10L)
                .nombre("Cuaderno")
                .descripcion("Cuaderno universitario 100 hojas")
                .precio(new BigDecimal("1990.00"))
                .stock(50)
                .categoria(categoria)
                .proveedor(proveedor)
                .build();

        // Acceder a todos los getters
        assertThat(producto.getId()).isEqualTo(10L);
        assertThat(producto.getNombre()).isEqualTo("Cuaderno");
        assertThat(producto.getDescripcion()).contains("universitario");
        assertThat(producto.getPrecio()).isEqualByComparingTo("1990.00");
        assertThat(producto.getStock()).isEqualTo(50);
        assertThat(producto.getCategoria()).isEqualTo(categoria);
        assertThat(producto.getProveedor()).isEqualTo(proveedor);

        // Ejecución explícita de métodos de Lombok
        assertThat(producto.toString()).contains("nombre=Cuaderno");
        assertThat(producto.hashCode()).isNotZero();
        assertThat(producto.equals(producto)).isTrue();
    }

    @Test
    void testSettersAndEquals() {
        Producto p1 = new Producto();
        p1.setId(1L);
        p1.setNombre("Lápiz");
        p1.setDescripcion("HB");
        p1.setPrecio(BigDecimal.ONE);
        p1.setStock(100);
        p1.setCategoria(new Categoria());
        p1.setProveedor(new Proveedor());

        Producto p2 = new Producto();
        p2.setId(1L);
        p2.setNombre("Lápiz");
        p2.setDescripcion("HB");
        p2.setPrecio(BigDecimal.ONE);
        p2.setStock(100);
        p2.setCategoria(new Categoria());
        p2.setProveedor(new Proveedor());

        // Verificación
        assertThat(p1).isEqualTo(p2);
        assertThat(p1.hashCode()).isEqualTo(p2.hashCode());
        assertThat(p1.toString()).contains("nombre=Lápiz");
    }
}
