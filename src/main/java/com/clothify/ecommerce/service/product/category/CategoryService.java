package com.clothify.ecommerce.service.product.category;

import com.clothify.ecommerce.dto.product.category.CategoryDTO;
import com.clothify.ecommerce.entity.product.ProductEntity;
import com.clothify.ecommerce.entity.product.category.CategoryEntity;
import com.clothify.ecommerce.exception.product.CategoryNotFoundException;
import com.clothify.ecommerce.exception.product.ProductNotFoundException;
import com.clothify.ecommerce.repository.product.ProductDAO;
import com.clothify.ecommerce.repository.product.category.CategoryDAO;
import com.clothify.ecommerce.service.product.image.ImageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryDAO categoryDAO;
    private final ProductDAO productDAO;
    private final ObjectMapper mapper;
    private final ImageService imageService;

    public CategoryDTO persist(String categoryDTO, MultipartFile image) throws IOException {
        CategoryEntity category = mapper.readValue(categoryDTO, CategoryEntity.class);
        Integer id = imageService.saveImageAndReturnId(image);
        category.setImageId(id);
        return mapper.convertValue(
                categoryDAO.save(
                        mapper.convertValue(category, CategoryEntity.class)), CategoryDTO.class);
    }

    public Set<CategoryDTO> retrieveAll() {
        Set<CategoryDTO> categorySet = new HashSet<>();
        categoryDAO.findAll().forEach(categoryEntity ->
                categorySet.add(mapper.convertValue(categoryEntity, CategoryDTO.class)));
        return categorySet;
    }

    public void assignCategoryToProduct(Integer productId, Integer categoryId) {
        ProductEntity product = productDAO.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        CategoryEntity category = categoryDAO.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("Category Not Found"));
        product.setCategory(category);
        productDAO.save(product);
    }

    public CategoryDTO update(CategoryDTO categoryDTO) {
        CategoryEntity category = categoryDAO.findById(
                categoryDTO.getId()).orElseThrow(() ->
                new CategoryNotFoundException("category not found"));
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return mapper.convertValue(categoryDAO.save(category), CategoryDTO.class);
    }

    public Set<CategoryDTO> retrieveAllByMainCategory(String mainCategory) {
        Set<CategoryDTO> categorySet = new HashSet<>();
        categoryDAO.findAllByMainCategory(mainCategory).forEach(categoryEntity ->
                categorySet.add(mapper.convertValue(categoryEntity, CategoryDTO.class)));
        return categorySet;
    }

    public CategoryDTO findById(Integer categoryId) {
        return mapper.convertValue(categoryDAO.findById(categoryId), CategoryDTO.class);
    }

    public boolean updateCategoryWithImage(MultipartFile image, String categoryDTO) throws IOException {
        CategoryEntity category = mapper.readValue(categoryDTO, CategoryEntity.class);
        Integer imageId = category.getImageId();
        imageService.updateImage(image, imageId);
        categoryDAO.save(category);
        return true;
    }

    public boolean updateCategoryWithoutImage(String categoryDTO) throws JsonProcessingException {
        CategoryEntity category = mapper.readValue(categoryDTO, CategoryEntity.class);
        categoryDAO.save(category);
        return true;
    }

    public boolean deleteById(Integer categoryId) {
        try {
            categoryDAO.deleteById(categoryId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
