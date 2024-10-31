package com.clothify.ecommerce.controller.product.category;

import com.clothify.ecommerce.dto.SuccessResponse;
import com.clothify.ecommerce.dto.product.category.ProductCategoryDTO;
import com.clothify.ecommerce.service.product.category.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/product/category")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @PostMapping()
    public SuccessResponse persist(@RequestBody ProductCategoryDTO productCategoryDTO) {
        productCategoryService.persist(productCategoryDTO);
        return SuccessResponse.builder().msg("Category Added").code(200).build();
    }

    @GetMapping("/{categoryId}")
    public ProductCategoryDTO retrieve(@PathVariable Integer categoryId) {
        return productCategoryService.retrieve(categoryId);
    }

    @GetMapping("")
    public List<ProductCategoryDTO> retrieveAllCategories() {
        return productCategoryService.retrieveAll();
    }

    @PatchMapping("")
    public SuccessResponse updateCategory(@RequestBody ProductCategoryDTO productCategoryDTO) {
        return productCategoryService.update(productCategoryDTO);
    }

    @DeleteMapping("/{categoryId}")
    public SuccessResponse deleteCategory(@PathVariable Integer categoryId) {
        return productCategoryService.delete(categoryId);
    }


}
