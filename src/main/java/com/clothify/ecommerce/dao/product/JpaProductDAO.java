package com.clothify.ecommerce.dao.product;

import com.clothify.ecommerce.entity.product.ProductEntity;
import org.springframework.data.repository.CrudRepository;

public interface JpaProductDAO extends CrudRepository<ProductEntity, Integer> {
}
