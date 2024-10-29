package com.clothify.ecommerce.service;

import com.clothify.ecommerce.dao.ProductDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductDAO productDAO;

    @Override
    public String retrieveProductById(String productId) {
        return productDAO.retrieveProductById(productId);
    }
}
