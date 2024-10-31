package com.clothify.ecommerce.service.product.category;

import com.clothify.ecommerce.dto.SuccessResponse;
import com.clothify.ecommerce.dto.product.category.ProductCategoryDTO;

import java.util.List;

public interface ProductCategoryService {
    void persist(ProductCategoryDTO productCategoryDTO);

    ProductCategoryDTO retrieve(Integer categoryId);

    SuccessResponse update(ProductCategoryDTO productCategoryDTO);

    List<ProductCategoryDTO> retrieveAll();

    SuccessResponse delete(Integer categoryId);
}
