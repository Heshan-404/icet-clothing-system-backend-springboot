package com.clothify.ecommerce.controller.product.category;

import com.clothify.ecommerce.dto.product.category.CategoryDTO;
import com.clothify.ecommerce.service.product.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("/product/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CategoryDTO persist(@RequestParam("category") String categoryDTO, @RequestParam("image") MultipartFile image) throws IOException {
        return categoryService.persist(categoryDTO, image);
    }

    @GetMapping("/all")
    public Set<CategoryDTO> retrieveAll() {
        return categoryService.retrieveAll();
    }

    @GetMapping("/all/{mainCategory}")
    public Set<CategoryDTO> retrieveAllByMainCategory(@PathVariable String mainCategory) {
        return categoryService.retrieveAllByMainCategory(mainCategory);
    }

    @GetMapping("/{categoryId}")
    public CategoryDTO getById(@PathVariable Integer categoryId) {
        return categoryService.findById(categoryId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/product/{productId}/category/{categoryId}")
    public void assignCategoryToProduct(@PathVariable Integer productId, @PathVariable Integer categoryId) {
        categoryService.assignCategoryToProduct(productId, categoryId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping
    public CategoryDTO updateCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.update(categoryDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(value = "/{categoryId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> updateCategoryWithImage(@RequestParam("category") String categoryDTO, @RequestParam(value = "image", required = false) MultipartFile image, @PathVariable Integer categoryId) throws IOException {
        if (image != null) {
            return categoryService.updateCategoryWithImage(image, categoryDTO) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
        } else {
            return categoryService.updateCategoryWithoutImage(categoryDTO) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{categoryId}")
    public ResponseEntity<Object> deleteCategoryById(@PathVariable Integer categoryId) {

        return categoryService.deleteById(categoryId) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
