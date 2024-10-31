package com.clothify.ecommerce.dao.product;

import com.clothify.ecommerce.entity.product.ProductEntity;
import com.clothify.ecommerce.entity.product.image.ProductImageEntity;

import java.util.List;

public interface ProductDAO {
    ProductEntity retrieveProductById(Integer productId);

    List<ProductEntity> retrieveProductList();

    void persist(ProductEntity product);

    Integer persistImage(ProductImageEntity productImageEntity);

    ProductImageEntity getImageById(Integer imageId);

    List<ProductEntity> retrieveProductListByCategoryId(Integer categoryId);

    void udpate(ProductEntity productEntity);

    void deleteProduct(Integer productId);
}
