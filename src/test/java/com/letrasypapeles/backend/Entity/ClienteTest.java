package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.Test;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

import com.letrasypapeles.backend.entity.RoleEntity;

class ClienteTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        RoleEntity role = RoleEntity.builder().id(1L).name("USER").build();

        Cliente cliente = new Cliente(
                1L,
                "Juan",
                "juan@example.com",
                "1234",
                100,
                Set.of(role)
        );

        assertThat(cliente.getIdCliente()).isEqualTo(1L);
        assertThat(cliente.getNombre()).isEqualTo("Juan");
        assertThat(cliente.getEmail()).isEqualTo("juan@example.com");
        assertThat(cliente.getContraseña()).isEqualTo("1234");
        assertThat(cliente.getPuntosFidelidad()).isEqualTo(100);
        assertThat(cliente.getRoles()).containsExactly(role);
    }

    @Test
    void testBuilder() {
        RoleEntity role = RoleEntity.builder().id(2L).name("ADMIN").build();

        Cliente cliente = Cliente.builder()
                .idCliente(2L)
                .nombre("Ana")
                .email("ana@example.com")
                .contraseña("pass")
                .puntosFidelidad(50)
                .roles(Set.of(role))
                .build();

        assertThat(cliente.getNombre()).isEqualTo("Ana");
        assertThat(cliente.getRoles()).contains(role);
    }

    @Test
    void testSetters() {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(3L);
        cliente.setNombre("Luis");
        cliente.setEmail("luis@example.com");
        cliente.setContraseña("abc");
        cliente.setPuntosFidelidad(30);

        assertThat(cliente.getNombre()).isEqualTo("Luis");
        assertThat(cliente.getEmail()).isEqualTo("luis@example.com");
    }

    @Test
    void testEqualsAndHashCode() {
        Cliente c1 = Cliente.builder().idCliente(1L).email("a@a.com").build();
        Cliente c2 = Cliente.builder().idCliente(1L).email("a@a.com").build();

        assertThat(c1).isEqualTo(c2);
        assertThat(c1.hashCode()).isEqualTo(c2.hashCode());
    }

    @Test
    void testToString() {
        Cliente cliente = Cliente.builder()
                .idCliente(5L)
                .nombre("Pedro")
                .build();

        assertThat(cliente.toString()).contains("idCliente=5", "nombre=Pedro");
    }

    @Test
    void testEqualsWithDifferentObjects() {
        Cliente cliente1 = Cliente.builder().idCliente(1L).email("a@a.com").build();
        Cliente cliente2 = Cliente.builder().idCliente(2L).email("b@b.com").build();
        Cliente cliente3 = null;
        String distintoTipo = "no es cliente";

        assertThat(cliente1).isNotEqualTo(cliente2);        // diferentes valores
        assertThat(cliente1).isNotEqualTo(cliente3);        // null
        assertThat(cliente1).isNotEqualTo(distintoTipo);    // otro tipo
        assertThat(cliente1.equals(cliente1)).isTrue();     // igualdad a sí mismo
    }

    @Test
    void testHashCodeConsistency() {
        Cliente cliente = Cliente.builder().idCliente(1L).email("test@test.com").build();
        int hash1 = cliente.hashCode();
        int hash2 = cliente.hashCode();

        assertThat(hash1).isEqualTo(hash2); // misma instancia, mismo hash
    }


}
