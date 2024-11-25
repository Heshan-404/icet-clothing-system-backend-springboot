package com.clothify.ecommerce.controller.product;

import com.clothify.ecommerce.dto.product.ProductWithoutImageDataDTO;
import com.clothify.ecommerce.dto.product.UpdateProductDTO;
import com.clothify.ecommerce.entity.product.ProductEntity;
import com.clothify.ecommerce.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ProductEntity persist(@RequestBody ProductEntity product) {
        return productService.save(product);
    }

    @GetMapping("/all")
    public List<ProductWithoutImageDataDTO> retrieveAll() {

        return productService.retrieveAll();
    }

    @GetMapping("/all/category/{categoryId}")
    public List<ProductWithoutImageDataDTO> retrieveAllByCategory(@PathVariable Integer categoryId) {
        return productService.retrieveAllByCategoryId(categoryId);
    }

    @GetMapping("/{productId}")
    public ProductWithoutImageDataDTO getById(@PathVariable Integer productId) {
        return productService.retrieveById(productId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<Object> updateProduct(@RequestBody UpdateProductDTO newProduct) {
        if (Boolean.TRUE.equals(productService.update(newProduct))) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{productId}")
    public ResponseEntity<Object> deleteProductById(@PathVariable Integer productId) {
        return productService.delete(productId) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
