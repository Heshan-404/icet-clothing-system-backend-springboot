package com.clothify.ecommerce.dto.order;

import com.clothify.ecommerce.entity.order.orderitem.OrderItemEntity;
import com.clothify.ecommerce.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private Date createDate = new Date(System.currentTimeMillis());
    private Long totalCost;
    private String status = "PENDING";
    private UserEntity user;
    private List<OrderItemEntity> orderItems = new ArrayList<>();
}
