package com.clothify.ecommerce.entity.product.category;

import com.clothify.ecommerce.entity.product.ProductEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String name;
    private String description;
    @Column(name = "main_category")
    private String mainCategory;//M/F/K
    private Integer imageId;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "category")
    @JsonManagedReference
    private List<ProductEntity> productList;
}
