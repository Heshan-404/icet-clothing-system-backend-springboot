package com.clothify.ecommerce.dao.product.category;

import com.clothify.ecommerce.entity.product.category.ProductCategoryEntity;
import org.springframework.data.repository.CrudRepository;

public interface JpaProductCategoryDAO extends CrudRepository<ProductCategoryEntity, Integer> {
}
