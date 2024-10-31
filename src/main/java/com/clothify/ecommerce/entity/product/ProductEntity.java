package com.clothify.ecommerce.entity.product;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@Entity
@Builder
@Table(name = "product")
@NoArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "category_id")
    private Integer categoryId;

    private String name;
    private String description;
    private Double price;
    private Double discount;

    @Column(name = "product_sizes_count")
    private List<Integer> productSizesCount;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Column(name = "image_id_list")
    private List<Integer> imageIdList;
}
