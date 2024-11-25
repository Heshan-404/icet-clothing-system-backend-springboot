package com.clothify.ecommerce.entity.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private List<UserEntity> users;
}
