package com.clothify.ecommerce.dao.product.category;

import com.clothify.ecommerce.entity.product.category.ProductCategoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Primary
@RequiredArgsConstructor
public class ProductCategoryDAOImpl implements ProductCategoryDAO {

    private final JpaProductCategoryDAO jpaProductCategoryDAO;
    private final JdbcTemplate jdbcTemplate;


    @Override
    public void persist(ProductCategoryEntity productCategoryEntity) {
        jpaProductCategoryDAO.save(productCategoryEntity);
    }

    @Override
    public Optional<ProductCategoryEntity> retrieve(Integer categoryId) {
        return jpaProductCategoryDAO.findById(categoryId);
    }

    @Override
    public void update(ProductCategoryEntity productCategoryEntity) {
        jpaProductCategoryDAO.save(productCategoryEntity);
    }

    @Override
    public List<ProductCategoryEntity> retrieveAll() {
        return (List<ProductCategoryEntity>) jpaProductCategoryDAO.findAll();
    }

    @Override
    public void delete(Integer categoryId) {
        jpaProductCategoryDAO.deleteById(categoryId);
    }
}

