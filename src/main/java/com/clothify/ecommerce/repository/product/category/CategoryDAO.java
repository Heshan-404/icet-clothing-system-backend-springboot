package com.clothify.ecommerce.repository.product.category;

import com.clothify.ecommerce.entity.product.category.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryDAO extends JpaRepository<CategoryEntity, Integer> {

    @Query("SELECT c FROM CategoryEntity c WHERE c.mainCategory = :mainCategory")
    List<CategoryEntity> findAllByMainCategory(@Param("mainCategory") String mainCategory);
}
