package com.clothify.ecommerce.entity.order;

import com.clothify.ecommerce.entity.order.orderitem.OrderItemEntity;
import com.clothify.ecommerce.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "order_id")
    private Integer id;
    @Column(name = "create_date")
    private Date createDate = new Date(System.currentTimeMillis());
    private String status = "PENDING";
    @Column(name = "total_cost")
    private Long totalCost;
    @ManyToOne
    @JoinColumn(name = "user_email")
    private UserEntity user;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderItemEntity> orderItems;
}
