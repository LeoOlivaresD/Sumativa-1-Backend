package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PedidoTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        Cliente cliente = Cliente.builder().idCliente(1L).nombre("Lucía").build();
        Producto prod1 = Producto.builder().id(1L).nombre("Resma A4").build();
        Producto prod2 = Producto.builder().id(2L).nombre("Carpeta").build();
        LocalDateTime fecha = LocalDateTime.of(2025, 7, 16, 12, 0);

        Pedido pedido = new Pedido(1L, fecha, "ENVIADO", cliente, List.of(prod1, prod2));

        assertThat(pedido.getId()).isEqualTo(1L);
        assertThat(pedido.getFecha()).isEqualTo(fecha);
        assertThat(pedido.getEstado()).isEqualTo("ENVIADO");
        assertThat(pedido.getCliente().getNombre()).isEqualTo("Lucía");
        assertThat(pedido.getListaProductos()).containsExactly(prod1, prod2);
    }

    @Test
    void testBuilder() {
        Cliente cliente = Cliente.builder().idCliente(2L).nombre("Pedro").build();
        Producto producto = Producto.builder().id(3L).nombre("Lapicera").build();

        Pedido pedido = Pedido.builder()
                .id(2L)
                .fecha(LocalDateTime.now())
                .estado("PENDIENTE")
                .cliente(cliente)
                .listaProductos(List.of(producto))
                .build();

        assertThat(pedido.getEstado()).isEqualTo("PENDIENTE");
        assertThat(pedido.getCliente()).isEqualTo(cliente);
        assertThat(pedido.getListaProductos()).hasSize(1);
    }

    @Test
    void testSetters() {
        Pedido pedido = new Pedido();
        pedido.setId(3L);
        pedido.setEstado("CANCELADO");

        assertThat(pedido.getId()).isEqualTo(3L);
        assertThat(pedido.getEstado()).isEqualTo("CANCELADO");
    }

    @Test
    void testEqualsAndHashCode() {
        Pedido p1 = Pedido.builder().id(4L).estado("ENTREGADO").build();
        Pedido p2 = Pedido.builder().id(4L).estado("ENTREGADO").build();

        assertThat(p1).isEqualTo(p2);
        assertThat(p1.hashCode()).isEqualTo(p2.hashCode());
    }

    @Test
    void testToString() {
        Pedido pedido = Pedido.builder().id(5L).estado("PAGO").build();
        assertThat(pedido.toString()).contains("id=5", "estado=PAGO");
    }


    @Test
    void testEqualsWithDifferentObjects() {
        Pedido p1 = Pedido.builder().id(1L).estado("RESERVADO").build();
        Pedido p2 = Pedido.builder().id(2L).estado("CONFIRMADO").build();
        Pedido p3 = null;
        String otroObjeto = "pedido";

        assertThat(p1).isNotEqualTo(p2);         // objetos distintos
        assertThat(p1).isNotEqualTo(p3);         // null
        assertThat(p1).isNotEqualTo(otroObjeto); // tipo diferente
        assertThat(p1.equals(p1)).isTrue();      // sí mismo
    }

    @Test
    void testHashCodeConsistency() {
        Pedido pedido = Pedido.builder()
                .id(6L)
                .estado("PROCESADO")
                .build();

        int hash1 = pedido.hashCode();
        int hash2 = pedido.hashCode();

        assertThat(hash1).isEqualTo(hash2);
    }




}

