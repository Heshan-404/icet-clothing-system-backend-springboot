package com.clothify.ecommerce.service.product;

import com.clothify.ecommerce.dao.product.ProductDAO;
import com.clothify.ecommerce.dao.product.image.ProductImageDAO;
import com.clothify.ecommerce.dto.SuccessResponse;
import com.clothify.ecommerce.dto.product.ProductDTO;
import com.clothify.ecommerce.entity.product.ProductEntity;
import com.clothify.ecommerce.entity.product.image.ProductImageEntity;
import com.clothify.ecommerce.exception.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;
    private final ProductImageDAO productImageDAO;
    private final ObjectMapper objectMapper;

    @Override
    public ProductDTO retrieveProductById(Integer productId) {
        return objectMapper.convertValue(productDAO.retrieveProductById(productId), ProductDTO.class);
    }

    @Override
    public List<ProductDTO> retrieveProductList() {
        List<ProductDTO> productList = new ArrayList<>();
        productDAO.retrieveProductList().forEach(productEntity ->
                productList.add(objectMapper.convertValue(productEntity, ProductDTO.class)));
        return productList;
    }

    @Override
    public void persistProduct(ProductDTO product, List<MultipartFile> imageList) {
        List<Integer> productImageIdList = new ArrayList<>();
        imageList.forEach(multipartFile -> {

            try {
                productImageIdList.add(productImageDAO.persistImage(ProductImageEntity.builder().
                        imageName(multipartFile.getOriginalFilename()).
                        imageData(multipartFile.getBytes()).
                        imageType(multipartFile.getContentType()).build()));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        product.setImageIdList(productImageIdList);
        productDAO.persist(objectMapper.convertValue(product, ProductEntity.class));
    }

    @Override
    public List<ProductDTO> retrieveProductListByCategoryId(Integer categoryId) {
        List<ProductDTO> productList = new ArrayList<>();
        productDAO.retrieveProductListByCategoryId(categoryId).forEach(productEntity ->
                productList.add(objectMapper.convertValue(productEntity, ProductDTO.class)));
        return productList;
    }

    @Override
    public void updateProduct(ProductDTO productDTO, List<MultipartFile> imageList) {
        if (retrieveProductById(
                productDTO.getProductId()).getProductId().
                equals(productDTO.getProductId())) {
            List<Integer> productImageIdList = productDTO.getImageIdList();
            imageList.forEach(multipartFile -> {

                try {
                    productImageIdList.add(productImageDAO.persistImage(ProductImageEntity.builder().
                            imageName(multipartFile.getOriginalFilename()).
                            imageData(multipartFile.getBytes()).
                            imageType(multipartFile.getContentType()).build()));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            productDTO.setImageIdList(productImageIdList);
            productDAO.udpate(objectMapper.convertValue(productDTO, ProductEntity.class));
        } else {
            throw new NotFoundException("Product not found to update");
        }
    }

    @Override
    public SuccessResponse deleteProductByProductId(Integer productId) {
        ProductDTO productDTO = retrieveProductById(productId);
        List<Integer> imageIdList = productDTO.getImageIdList();
        productDAO.deleteProduct(productId);
        imageIdList.forEach(productImageDAO::deleteImageById);
        return SuccessResponse.builder().msg("Product `" + productDTO.getName() + "` deleted").code(200).build();
    }
}
