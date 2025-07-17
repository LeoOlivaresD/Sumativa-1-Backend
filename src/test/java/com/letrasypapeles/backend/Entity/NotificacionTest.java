package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class NotificacionTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        Cliente cliente = Cliente.builder().idCliente(1L).nombre("Maria").build();
        LocalDateTime fecha = LocalDateTime.of(2025, 7, 16, 10, 30);

        Notificacion noti = new Notificacion(1L, "Tu pedido fue enviado", fecha, cliente);

        assertThat(noti.getId()).isEqualTo(1L);
        assertThat(noti.getMensaje()).isEqualTo("Tu pedido fue enviado");
        assertThat(noti.getFecha()).isEqualTo(fecha);
        assertThat(noti.getCliente()).isEqualTo(cliente);
    }

    @Test
    void testBuilder() {
        Cliente cliente = Cliente.builder().idCliente(2L).nombre("Carlos").build();
        LocalDateTime fecha = LocalDateTime.now();

        Notificacion noti = Notificacion.builder()
                .id(2L)
                .mensaje("Bienvenido a Letras y Papeles")
                .fecha(fecha)
                .cliente(cliente)
                .build();

        assertThat(noti.getMensaje()).contains("Bienvenido");
        assertThat(noti.getCliente().getNombre()).isEqualTo("Carlos");
    }

    @Test
    void testSetters() {
        Notificacion noti = new Notificacion();
        noti.setId(3L);
        noti.setMensaje("Nuevo mensaje");
        noti.setFecha(LocalDateTime.of(2025, 7, 15, 18, 0));

        assertThat(noti.getId()).isEqualTo(3L);
        assertThat(noti.getMensaje()).isEqualTo("Nuevo mensaje");
        assertThat(noti.getFecha().getYear()).isEqualTo(2025);
    }

    @Test
    void testEqualsAndHashCode() {
        Notificacion n1 = Notificacion.builder().id(1L).mensaje("Hola").build();
        Notificacion n2 = Notificacion.builder().id(1L).mensaje("Hola").build();

        assertThat(n1).isEqualTo(n2);
        assertThat(n1.hashCode()).isEqualTo(n2.hashCode());
    }

    @Test
    void testToString() {
        Notificacion noti = Notificacion.builder()
                .id(4L)
                .mensaje("Aviso")
                .build();

        assertThat(noti.toString()).contains("id=4", "mensaje=Aviso");
    }
}
