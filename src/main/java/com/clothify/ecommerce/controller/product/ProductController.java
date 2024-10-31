package com.clothify.ecommerce.controller.product;

import com.clothify.ecommerce.dto.product.ProductDTO;
import com.clothify.ecommerce.dto.SuccessResponse;
import com.clothify.ecommerce.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    public ProductDTO retrieve(@PathVariable Integer productId) {
        return productService.retrieveProductById(productId);
    }

    @GetMapping("")
    public List<ProductDTO> retrieveAll() {
        return productService.retrieveProductList();
    }


    @PostMapping(path = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse persist(@RequestPart(value = "images") List<MultipartFile> imageFileList, @RequestPart(value = "product") ProductDTO productDTO) throws IOException {
        productService.persistProduct(productDTO, imageFileList);
        return SuccessResponse.builder().msg("Product Added").code(200).build();
    }

    @GetMapping("/by-category/{categoryId}")
    public List<ProductDTO> retrieveAllByCategoryId(@PathVariable Integer categoryId) {
        return productService.retrieveProductListByCategoryId(categoryId);
    }

    @PatchMapping(path = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse update(@RequestPart(value = "images") List<MultipartFile> imageFileList, @RequestPart(value = "product") ProductDTO productDTO) {
        productService.updateProduct(productDTO, imageFileList);
        return SuccessResponse.builder().msg("Product Updated").code(200).build();
    }

    @DeleteMapping("/{productId}")
    public SuccessResponse deleteProductByProductId(@PathVariable Integer productId) {
        return productService.deleteProductByProductId(productId);
    }
}
