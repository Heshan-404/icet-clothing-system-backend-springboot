package com.clothify.ecommerce.repository.order;

import com.clothify.ecommerce.entity.order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDAO extends JpaRepository<OrderEntity, Integer> {
    @Query(value = "SELECT o.order_id AS id," +
            " o.create_date AS createDate, " +
            "o.status AS status," +
            " o.total_cost AS totalCost," +
            " u.email AS userEmail, " +
            "oi.qty AS qty," +
            " oi.price AS price," +
            " p.id AS productId, " +
            "oi.size_id AS sizeId " +
            "FROM orders o " +
            "JOIN users u ON o.user_email = u.email " +
            "JOIN order_item oi ON o.order_id = oi.order_id " +
            "JOIN product p ON oi.product_id = p.id " +
            "WHERE o.order_id = :id", nativeQuery = true)
    List<Object[]> findOrderDetailsById(@Param("id") Integer id);

    @Query(value = "SELECT o.order_id AS id, " +
            "o.create_date AS createDate, " +
            "o.status AS status, " +
            "o.total_cost AS totalCost, " +
            "u.email AS userEmail, " +
            "oi.qty AS qty, " +
            "oi.price AS price, " +
            "p.id AS productId, " +
            "oi.size_id AS sizeId " +
            "FROM orders o " +
            "JOIN users u ON o.user_email = u.email " +
            "JOIN order_item oi ON o.order_id = oi.order_id " +
            "JOIN product p ON oi.product_id = p.id " +
            "ORDER BY o.order_id ASC", nativeQuery = true)
    List<Object[]> findAllOrdersSortedByCreateDate();


}
