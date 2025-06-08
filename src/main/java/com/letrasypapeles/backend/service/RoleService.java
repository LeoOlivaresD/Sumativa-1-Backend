package com.letrasypapeles.backend.service;

import com.letrasypapeles.backend.entity.RoleEntity;
import com.letrasypapeles.backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<RoleEntity> obtenerTodos() {
        return roleRepository.findAll();
    }

    public Optional<RoleEntity> obtenerPorNombre(String nombre) {
        return roleRepository.findByName(nombre);
    }

    public RoleEntity guardar(RoleEntity role) {
        return roleRepository.save(role);
    }

    public void eliminar(String nombre) {
        roleRepository.deleteByName(nombre);
    }
}
