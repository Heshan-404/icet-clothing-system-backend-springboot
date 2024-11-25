package com.clothify.ecommerce.dto.product.category;

import lombok.Data;

@Data
public class CategoryDTO {
    private Integer id;
    private String name;
    private String description;
    private String mainCategory; //M,F,K
    private Integer imageId;
}
