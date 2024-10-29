package com.clothify.ecommerce.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class ProductDAOImpl implements ProductDAO {
    @Override
    public String retrieveProductById(String productId) {
        return productId;
    }
}

