package com.clothify.ecommerce.dto.product;

import lombok.Data;

import java.util.*;

@Data
public class UpdateProductDTO {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Double discount;
    private List<Integer> stockQty = Arrays.asList(0, 0, 0, 0, 0, 0);
}
