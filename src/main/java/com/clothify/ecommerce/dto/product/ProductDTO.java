package com.clothify.ecommerce.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Integer productId;
    private Integer categoryId;
    private String name;
    private String description;
    private Double price;
    private Double discount;
    private List<Integer> productSizesCount;
    private List<Integer> imageIdList;
    private LocalDate createdDate;
    private LocalDate updatedDate;

    public ProductDTO(Integer categoryId, String name, String description, Double price, Double discount, List<Integer> productSizesCount, LocalDate createdDate, LocalDate updatedDate) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.productSizesCount = productSizesCount;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

}
