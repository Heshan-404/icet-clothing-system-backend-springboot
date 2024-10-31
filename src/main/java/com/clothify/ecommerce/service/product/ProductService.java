package com.clothify.ecommerce.service.product;

import com.clothify.ecommerce.dto.SuccessResponse;
import com.clothify.ecommerce.dto.product.ProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductDTO retrieveProductById(Integer productId);

    List<ProductDTO> retrieveProductList();

    void persistProduct(ProductDTO productDTO, List<MultipartFile> imageFile) throws IOException;

    List<ProductDTO> retrieveProductListByCategoryId(Integer categoryId);

    void updateProduct(ProductDTO productDTO, List<MultipartFile> imageFileList);

    SuccessResponse deleteProductByProductId(Integer productId);
}
