package com.clothify.ecommerce.service.product.category;

import com.clothify.ecommerce.dao.product.category.ProductCategoryDAO;
import com.clothify.ecommerce.dto.SuccessResponse;
import com.clothify.ecommerce.dto.product.category.ProductCategoryDTO;
import com.clothify.ecommerce.entity.product.category.ProductCategoryEntity;
import com.clothify.ecommerce.exception.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Primary
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryDAO productCategoryDAO;
    private final ObjectMapper objectMapper;


    @Override
    public void persist(ProductCategoryDTO productCategoryDTO) {
        productCategoryDAO.persist(objectMapper.convertValue(productCategoryDTO, ProductCategoryEntity.class));
    }

    @Override
    public ProductCategoryDTO retrieve(Integer categoryId) {
        Optional<ProductCategoryEntity> productCategoryEntity = productCategoryDAO.retrieve(categoryId);
        if (productCategoryEntity.isEmpty()) throw new NotFoundException("Category Not Found");
        return objectMapper.convertValue(productCategoryEntity, ProductCategoryDTO.class);
    }

    @Override
    public SuccessResponse update(ProductCategoryDTO productCategoryDTO) {
        ProductCategoryDTO retrieve = retrieve(productCategoryDTO.getCategoryId());
        if (Objects.equals(retrieve.getCategoryId(), productCategoryDTO.getCategoryId())) {
            productCategoryDAO.update(objectMapper.convertValue(productCategoryDTO, ProductCategoryEntity.class));
            return SuccessResponse.builder().msg("Category updated").code(200).build();
        } else {
            throw new NotFoundException("Update category not found");
        }
    }

    @Override
    public List<ProductCategoryDTO> retrieveAll() {
        List<ProductCategoryEntity> productCategoryEntities = productCategoryDAO.retrieveAll();
        List<ProductCategoryDTO> productCategoryDTOS = new ArrayList<>();
        productCategoryEntities.forEach(productCategoryEntity -> productCategoryDTOS.add(
                objectMapper.convertValue(productCategoryEntity, ProductCategoryDTO.class)));
        if (productCategoryDTOS.isEmpty()) throw new NotFoundException("Not found any category");
        return productCategoryDTOS;
    }

    @Override
    public SuccessResponse delete(Integer categoryId) {
        ProductCategoryDTO retrieve = retrieve(categoryId);
        if (Objects.equals(retrieve.getCategoryId(), categoryId)) {
            productCategoryDAO.delete(categoryId);
            return SuccessResponse.builder().msg("Category `" + categoryId + "` deleted").code(200).build();
        } else {
            throw new NotFoundException("Delete category not found");
        }
    }
}
