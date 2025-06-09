package com.letrasypapeles.backend.repository;

import com.letrasypapeles.backend.entity.Cliente;
import com.letrasypapeles.backend.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<UserEntity> findByNombre(String username);

    Optional<Cliente> findByEmail(String email);

    boolean existsByEmail(String email);
}
