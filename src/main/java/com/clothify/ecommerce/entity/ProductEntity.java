package com.clothify.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    private String productId;
    private String categoryId;
    private String name;
    private String description;
    private Double price;
    private Double discount;
    private String productSizeId;
    private LocalDate createdDate;
    private LocalDate updatedDate;
}
