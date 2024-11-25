package com.clothify.ecommerce.entity.order.orderitem;

import com.clothify.ecommerce.entity.product.ProductEntity;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "order_item")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private Integer qty;
    private Double price;
    @Column(name = "size_id", nullable = false)
    private Integer sizeId;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;


}
