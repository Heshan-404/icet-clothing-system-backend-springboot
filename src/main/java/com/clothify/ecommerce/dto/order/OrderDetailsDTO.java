package com.clothify.ecommerce.dto.order;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDetailsDTO {
    private Integer id;
    private String status;
    private Long totalCost;
    private Date createDate;
    private String userEmail;
    private List<OrderItemDTO> orderItems;

    @Data
    public static class OrderItemDTO {
        private Double price;
        private ProductDTO product;

        @Data
        public static class ProductDTO {
            private Integer id;
            private Integer qty;
            private Integer sizeId;
        }
    }
}

