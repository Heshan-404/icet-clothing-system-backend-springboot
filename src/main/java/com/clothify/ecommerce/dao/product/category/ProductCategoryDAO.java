package com.clothify.ecommerce.dao.product.category;

import com.clothify.ecommerce.entity.product.category.ProductCategoryEntity;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryDAO {

    void persist(ProductCategoryEntity productCategoryEntity);

    Optional<ProductCategoryEntity> retrieve(Integer categoryId);

    void update(ProductCategoryEntity productCategoryEntity);

    List<ProductCategoryEntity> retrieveAll();

    void delete(Integer categoryId);
}
