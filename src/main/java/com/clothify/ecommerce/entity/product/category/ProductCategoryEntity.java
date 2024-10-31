package com.clothify.ecommerce.entity.product.category;

import jakarta.persistence.*;
import lombok.*;

@Data
@ToString
@AllArgsConstructor
@Entity
@Table(name = "product_category")
@NoArgsConstructor
@Builder
public class ProductCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "category_name")
    private String categoryName;

    private Integer gender;
    private String description;
}
