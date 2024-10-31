package com.clothify.ecommerce.dao.product.image;

import com.clothify.ecommerce.entity.product.image.ProductImageEntity;


public interface ProductImageDAO {

    Integer persistImage(ProductImageEntity productImageEntity);

    ProductImageEntity getImageById(Integer imageId);

    void deleteImageById(Integer imageId);
}
