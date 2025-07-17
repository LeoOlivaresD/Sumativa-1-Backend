package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserEntityTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        RoleEntity role = new RoleEntity("ADMIN");
        role.setId(1L);

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("admin");
        user.setPassword("securepass");
        user.setRoles(List.of(role));

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getUsername()).isEqualTo("admin");
        assertThat(user.getPassword()).isEqualTo("securepass");
        assertThat(user.getRoles()).containsExactly(role);
    }

    @Test
    void testEqualsAndHashCode() {
        UserEntity u1 = new UserEntity();
        u1.setId(10L);
        u1.setUsername("user");

        UserEntity u2 = new UserEntity();
        u2.setId(10L);
        u2.setUsername("user");

        assertThat(u1).isEqualTo(u2);
        assertThat(u1.hashCode()).isEqualTo(u2.hashCode());
    }

    @Test
    void testToString() {
        UserEntity user = new UserEntity();
        user.setId(2L);
        user.setUsername("tester");

        assertThat(user.toString()).contains("id=2", "username=tester");
    }
}
