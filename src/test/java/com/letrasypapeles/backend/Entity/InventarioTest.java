package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import com.letrasypapeles.backend.entity.Inventario;

class InventarioTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        Producto producto = Producto.builder().id(1L).nombre("Lápiz").build();
        Sucursal sucursal = Sucursal.builder().id(1L).nombre("Sucursal Centro").build();

        Inventario inventario = new Inventario(1L, 100, 20, producto, sucursal);

        assertThat(inventario.getId()).isEqualTo(1L);
        assertThat(inventario.getCantidad()).isEqualTo(100);
        assertThat(inventario.getUmbral()).isEqualTo(20);
        assertThat(inventario.getProducto()).isEqualTo(producto);
        assertThat(inventario.getSucursal()).isEqualTo(sucursal);
    }

    @Test
    void testBuilder() {
        Producto producto = Producto.builder().id(2L).nombre("Cuaderno").build();
        Sucursal sucursal = Sucursal.builder().id(2L).nombre("Sucursal Norte").build();

        Inventario inv = Inventario.builder()
                .id(2L)
                .cantidad(50)
                .umbral(10)
                .producto(producto)
                .sucursal(sucursal)
                .build();

        assertThat(inv.getCantidad()).isEqualTo(50);
        assertThat(inv.getProducto().getNombre()).isEqualTo("Cuaderno");
    }

    @Test
    void testSetters() {
        Inventario inv = new Inventario();
        inv.setCantidad(200);
        inv.setUmbral(30);

        assertThat(inv.getCantidad()).isEqualTo(200);
        assertThat(inv.getUmbral()).isEqualTo(30);
    }

    @Test
    void testToString() {
        Inventario inv = Inventario.builder()
                .id(10L)
                .cantidad(5)
                .umbral(1)
                .build();

        assertThat(inv.toString()).contains("id=10", "cantidad=5", "umbral=1");
    }

    @Test
    void testEqualsAndHashCode() {
        Inventario i1 = Inventario.builder().id(1L).build();
        Inventario i2 = Inventario.builder().id(1L).build();

        assertThat(i1).isEqualTo(i2);
        assertThat(i1.hashCode()).isEqualTo(i2.hashCode());
    }
}
