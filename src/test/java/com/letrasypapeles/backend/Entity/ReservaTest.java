package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ReservaTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        Cliente cliente = Cliente.builder().idCliente(1L).nombre("Camila").build();
        Producto producto = Producto.builder().id(1L).nombre("Archivador").build();
        LocalDateTime fecha = LocalDateTime.of(2025, 7, 20, 9, 30);

        Reserva reserva = new Reserva(1L, fecha, "CONFIRMADA", cliente, producto);

        assertThat(reserva.getId()).isEqualTo(1L);
        assertThat(reserva.getFechaReserva()).isEqualTo(fecha);
        assertThat(reserva.getEstado()).isEqualTo("CONFIRMADA");
        assertThat(reserva.getCliente()).isEqualTo(cliente);
        assertThat(reserva.getProducto()).isEqualTo(producto);
    }

    @Test
    void testBuilder() {
        Cliente cliente = Cliente.builder().idCliente(2L).nombre("Diego").build();
        Producto producto = Producto.builder().id(2L).nombre("Carpeta Azul").build();

        Reserva reserva = Reserva.builder()
                .id(2L)
                .fechaReserva(LocalDateTime.now())
                .estado("PENDIENTE")
                .cliente(cliente)
                .producto(producto)
                .build();

        assertThat(reserva.getEstado()).isEqualTo("PENDIENTE");
        assertThat(reserva.getProducto().getNombre()).isEqualTo("Carpeta Azul");
    }

    @Test
    void testSetters() {
        Reserva reserva = new Reserva();
        reserva.setId(3L);
        reserva.setEstado("CANCELADA");

        assertThat(reserva.getId()).isEqualTo(3L);
        assertThat(reserva.getEstado()).isEqualTo("CANCELADA");
    }

    @Test
    void testEqualsAndHashCode() {
        Reserva r1 = Reserva.builder().id(5L).estado("X").build();
        Reserva r2 = Reserva.builder().id(5L).estado("X").build();

        assertThat(r1).isEqualTo(r2);
        assertThat(r1.hashCode()).isEqualTo(r2.hashCode());
    }

    @Test
    void testToString() {
        Reserva reserva = Reserva.builder().id(6L).estado("TERMINADA").build();
        assertThat(reserva.toString()).contains("id=6", "estado=TERMINADA");
    }
}
