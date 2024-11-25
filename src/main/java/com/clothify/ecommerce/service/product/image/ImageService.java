package com.clothify.ecommerce.service.product.image;

import com.clothify.ecommerce.dto.product.image.ImageDTO;
import com.clothify.ecommerce.entity.product.ProductEntity;
import com.clothify.ecommerce.entity.product.image.ImageEntity;
import com.clothify.ecommerce.exception.product.ImageNotFoundException;
import com.clothify.ecommerce.repository.product.ProductDAO;
import com.clothify.ecommerce.repository.product.category.CategoryDAO;
import com.clothify.ecommerce.repository.product.image.ImageDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageDAO imageDAO;
    private final ProductDAO productDAO;
    private final ObjectMapper mapper;
    private final CategoryDAO categoryDAO;

    @Transactional
    public Boolean saveImageToProduct(List<ImageDTO> imageList, Integer productId) {
        ProductEntity productEntity = productDAO.findById(productId).orElse(null);

        if (productEntity != null) {
            imageList.forEach(imageDTO -> {
                ImageEntity imageEntity = mapper.convertValue(imageDTO, ImageEntity.class);
                productEntity.getImageList().add(imageEntity);
            });
            productDAO.save(productEntity);
            return true;
        }
        return false;
    }


    public ImageDTO retrieve(Integer imageId) {
        ImageEntity imageEntity = imageDAO.findById(imageId).orElseThrow(() -> {
            throw new ImageNotFoundException("Image Not Found");
        });
        return mapper.convertValue(imageEntity, ImageDTO.class);
    }

    public Boolean removeAllByProductId(Integer productId) {
        return !imageDAO.deleteAllByProductId(productId).isEmpty();
    }

    public Boolean removeByImageId(Integer imageId) {
        imageDAO.deleteById(imageId);
        return imageDAO.findById(imageId).isEmpty();
    }

    public Integer saveImageAndReturnId(MultipartFile image) throws IOException {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setName(image.getName());
        imageEntity.setData(image.getBytes());
        ImageEntity save = imageDAO.save(imageEntity);
        return save.getId();
    }

    public void updateImage(MultipartFile image, Integer imageId) throws IOException {
        ImageEntity imageEntity = imageDAO.findById(imageId).orElseThrow();
        imageEntity.setData(image.getBytes());
        imageEntity.setName(image.getName());
        imageDAO.save(imageEntity);
    }
}
