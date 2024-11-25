package com.clothify.ecommerce.dto.order.orderitem;

import com.clothify.ecommerce.dto.product.ProductDTO;
import lombok.Data;

@Data
public class OrderItemDTO {
    private Integer id;
    private Integer sizeId;
    private Integer qty;
    private Double price;
    private ProductDTO product;
}
