package com.clothify.ecommerce.repository.product;

import com.clothify.ecommerce.entity.product.ProductEntity;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDAO extends JpaRepository<ProductEntity, Integer> {
    @Query(value = "SELECT p.id AS productId, " +
            "p.name AS productName, " +
            "p.description, " +
            "p.price, " +
            "p.stock_qty AS stockQty, " +
            "p.discount, " +
            "p.update_date AS updateDate, " +
            "p.created_date AS createDate, " +
            "c.id AS categoryId, " +
            "c.name AS categoryName, " +
            "c.description AS categoryDescription, " +
            "ARRAY_AGG(i.id ORDER BY i.id) AS imageIds " +
            "FROM product p " +
            "LEFT JOIN category c ON p.category_id = c.id " +
            "LEFT JOIN image i ON p.id = i.product_id " +
            "GROUP BY p.id, c.id",
            nativeQuery = true)
    public List<Tuple> findAllProductsTuple();


    @Query(value = "SELECT p.id AS productId, " +
            "p.name AS productName, " +
            "p.description, " +
            "p.price, " +
            "p.stock_qty AS stockQty, " +
            "p.discount, " +
            "p.update_date AS updateDate, " +
            "p.created_date AS createDate, " +
            "c.id AS categoryId, " +
            "c.name AS categoryName, " +
            "c.description AS categoryDescription, " +
            "ARRAY_AGG(i.id ORDER BY i.id) AS imageIds " +
            "FROM product p " +
            "LEFT JOIN category c ON p.category_id = c.id " +
            "LEFT JOIN image i ON p.id = i.product_id " +
            "WHERE p.id = :productId " +
            "GROUP BY p.id, c.id",
            nativeQuery = true)
    Optional<Tuple> findProductById(@Param("productId") Integer productId);

    @Query(value = "SELECT p.id AS productId, " +
            "p.name AS productName, " +
            "p.description, " +
            "p.price, " +
            "p.stock_qty AS stockQty, " +
            "p.discount, " +
            "p.update_date AS updateDate, " +
            "p.created_date AS createDate, " +
            "c.id AS categoryId, " +
            "c.name AS categoryName, " +
            "c.description AS categoryDescription, " +
            "ARRAY_AGG(i.id ORDER BY i.id) AS imageIds " +
            "FROM product p " +
            "LEFT JOIN category c ON p.category_id = c.id " +
            "LEFT JOIN image i ON p.id = i.product_id " +
            "WHERE c.id = :categoryId " +
            "GROUP BY p.id, c.id",
            nativeQuery = true)
    List<Tuple> findAllByCategoryId(@Param("categoryId") Integer categoryId);

    @Modifying
    @Query(value = "UPDATE product " +
            "SET name = :name, " +
            "price = :price, " +
            "description = :description, " +
            "discount = :discount, " +
            "stock_qty = :stockQty " +
            "WHERE id = :id",
            nativeQuery = true)
    int updateProduct(@Param("id") Integer id,
                      @Param("name") String name,
                      @Param("price") Double price,
                      @Param("description") String description,
                      @Param("discount") Double discount,
                      @Param("stockQty") Integer[] stockQty);
}
