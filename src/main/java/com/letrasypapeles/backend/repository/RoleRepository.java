package com.letrasypapeles.backend.repository;

import com.letrasypapeles.backend.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);

    void deleteByName(String name);
}
