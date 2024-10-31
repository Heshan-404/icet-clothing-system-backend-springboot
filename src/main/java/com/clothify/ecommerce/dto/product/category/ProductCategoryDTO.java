package com.clothify.ecommerce.dto.product.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryDTO {
    private Integer categoryId;
    private String categoryName;
    private Integer gender;
    private String description;

    public ProductCategoryDTO(String categoryName, Integer gender, String description) {
        this.categoryName = categoryName;
        this.gender = gender;
        this.description = description;
    }
}
