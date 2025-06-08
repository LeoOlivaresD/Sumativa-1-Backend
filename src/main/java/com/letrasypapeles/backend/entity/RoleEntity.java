package com.letrasypapeles.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class RoleEntity {
    public RoleEntity(String string) {
        // TODO Auto-generated constructor stub
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

}
