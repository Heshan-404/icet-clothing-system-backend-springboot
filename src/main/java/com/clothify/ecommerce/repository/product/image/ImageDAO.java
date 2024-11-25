package com.clothify.ecommerce.repository.product.image;

import com.clothify.ecommerce.entity.product.image.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageDAO extends JpaRepository<ImageEntity, Integer> {
    @Query(value = "DELETE FROM image WHERE product_id = :productId RETURNING id", nativeQuery = true)
    List<Integer> deleteAllByProductId(@Param("productId") Integer productId);
}
