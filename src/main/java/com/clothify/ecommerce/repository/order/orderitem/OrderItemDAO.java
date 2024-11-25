package com.clothify.ecommerce.repository.order.orderitem;

import com.clothify.ecommerce.entity.order.orderitem.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemDAO extends JpaRepository<OrderItemEntity,Integer> {
}
