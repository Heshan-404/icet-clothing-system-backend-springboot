package com.clothify.ecommerce.dao.product.image;

import com.clothify.ecommerce.dao.product.JpaProductDAO;
import com.clothify.ecommerce.entity.product.image.ProductImageEntity;
import com.clothify.ecommerce.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
@RequiredArgsConstructor
public class ProductImageDAOImpl implements ProductImageDAO {

    private final JpaProductDAO jpaProductDAO;
    private final JpaProductImageDAO jpaProductImageDAO;
    private final JdbcTemplate jdbcTemplate;


    @Override
    public Integer persistImage(ProductImageEntity productImageEntity) {
        ProductImageEntity savedImage = jpaProductImageDAO.save(productImageEntity);
        return savedImage.getImageId();
    }

    @Override
    public ProductImageEntity getImageById(Integer imageId) {
        Optional<ProductImageEntity> byId = jpaProductImageDAO.findById(imageId);
        if (byId.isPresent()) return byId.get();
        throw new NotFoundException("Image Not Found");
    }

    @Override
    public void deleteImageById(Integer imageId) {
        jpaProductImageDAO.deleteById(imageId);
    }
}

