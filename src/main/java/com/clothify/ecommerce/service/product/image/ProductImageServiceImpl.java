package com.clothify.ecommerce.service.product.image;

import com.clothify.ecommerce.dao.product.ProductDAO;
import com.clothify.ecommerce.dto.product.image.ProductImageDTO;
import com.clothify.ecommerce.entity.product.image.ProductImageEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Primary
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductDAO productDAO;
    private final ObjectMapper objectMapper;

    @Override
    public Integer persistProductImage(Integer productId, MultipartFile image) throws IOException {
        ProductImageEntity build = ProductImageEntity.builder()
                .imageName(image.getOriginalFilename())
                .imageType(image.getContentType())
                .imageData(image.getBytes()).build();
        return productDAO.persistImage(build);
    }

    @Override
    public ProductImageDTO getProductImageById(Integer imageId) {
        return objectMapper.convertValue(productDAO.getImageById(imageId), ProductImageDTO.class);
    }
}
