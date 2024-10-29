package com.clothify.ecommerce.DTO;

import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductDTO {
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
