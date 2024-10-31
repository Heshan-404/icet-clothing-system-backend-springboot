package com.clothify.ecommerce.dao.product.image;

import com.clothify.ecommerce.entity.product.image.ProductImageEntity;
import org.springframework.data.repository.CrudRepository;

public interface JpaProductImageDAO extends CrudRepository<ProductImageEntity, Integer> {
}
