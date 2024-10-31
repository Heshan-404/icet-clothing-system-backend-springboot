package com.clothify.ecommerce.controller.product.image;

import com.clothify.ecommerce.dto.product.image.ProductImageDTO;
import com.clothify.ecommerce.service.product.image.ProductImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService productImageService;

    @GetMapping("/image/{imageId}")
    public ResponseEntity<ProductImageDTO> retrieveImageById(@PathVariable Integer imageId) {
        return ResponseEntity.accepted().body(productImageService.getProductImageById(imageId));
    }
}
