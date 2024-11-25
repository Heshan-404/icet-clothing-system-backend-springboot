package com.clothify.ecommerce.dto.product;

import com.clothify.ecommerce.entity.product.image.ImageEntity;
import lombok.Data;

import java.util.*;

@Data
public class ProductDTO {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Double discount;
    private Date updateDate = new Date(System.currentTimeMillis());
    private Date createdDate = new Date(System.currentTimeMillis());
    private Set<ImageEntity> imageList = new HashSet<>();
    private List<Integer> stockQty = Arrays.asList(0, 0, 0, 0, 0, 0);
}
