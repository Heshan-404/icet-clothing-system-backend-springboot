package com.clothify.ecommerce.service.product.image;

import com.clothify.ecommerce.dto.product.image.ProductImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductImageService {

    Integer persistProductImage(Integer productId, MultipartFile image) throws IOException;

    ProductImageDTO getProductImageById(Integer imageId);
}
