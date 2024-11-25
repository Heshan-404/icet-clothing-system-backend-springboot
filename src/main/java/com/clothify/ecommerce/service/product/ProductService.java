package com.clothify.ecommerce.service.product;

import com.clothify.ecommerce.dto.product.ProductWithoutImageDataDTO;
import com.clothify.ecommerce.dto.product.UpdateProductDTO;
import com.clothify.ecommerce.entity.product.ProductEntity;
import com.clothify.ecommerce.entity.product.category.CategoryEntity;
import com.clothify.ecommerce.exception.product.ProductNotFoundException;
import com.clothify.ecommerce.repository.product.ProductDAO;
import com.clothify.ecommerce.repository.product.category.CategoryDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDAO productDAO;
    private final ObjectMapper mapper;
    private final CategoryDAO categoryDAO;

    @Transactional
    public ProductEntity save(ProductEntity product) {
        CategoryEntity category = categoryDAO.findById(product.getCategory().getId()).orElse(null);

        product.setCategory(category);
        return productDAO.save(
                mapper.convertValue(
                        product, ProductEntity.class));
    }

    public List<ProductWithoutImageDataDTO> retrieveAll() {
        List<Tuple> tuples = productDAO.findAllProductsTuple(); // Assuming you are fetching data as Tuple
        List<ProductWithoutImageDataDTO> products = new ArrayList<>();

        for (Tuple tuple : tuples) {
            ProductWithoutImageDataDTO productWithoutImageDataDTO = convertToDTO(tuple);
            products.add(productWithoutImageDataDTO);
        }
        return products;
    }


    public ProductWithoutImageDataDTO retrieveById(Integer productId) {
        Optional<Tuple> tuple = productDAO.findProductById(productId);
        if (tuple.isPresent()) {
            return convertToDTO(tuple.get());
        } else {
            throw new ProductNotFoundException("Product not found");
        }
    }

    private ProductWithoutImageDataDTO convertToDTO(Tuple tuple) {
//        cockroachDb
//        Integer productId = tuple.get("productId") != null ? ((Long) tuple.get("productId")).intValue() : null;
        Integer productId = tuple.get("productId") != null ? ((Integer) tuple.get("productId")) : null;

        String productName = (String) tuple.get("productName");
        String description = (String) tuple.get("description");
        Double price = (Double) tuple.get("price");

        Integer[] stockQtyArray = (Integer[]) tuple.get("stockQty");
        List<Integer> stockQty = Arrays.asList(stockQtyArray);
//      For cockroachDb
//        Long[] stockQtyArray = (Long[]) tuple.get("stockQty");
//        List<Integer> stockQty = Arrays.stream(stockQtyArray)
//                .map(Long::intValue)
//                .collect(Collectors.toList());

        Double discount = (Double) tuple.get("discount");
        java.util.Date updateDate = (java.util.Date) tuple.get("updateDate");
        java.util.Date createDate = (java.util.Date) tuple.get("createDate");

//        cockroachDb
//        Long[] imageIdsArray = (Long[]) tuple.get("imageIds");
//        List<Integer> imageIds = Arrays.stream(imageIdsArray)
//                .map(Long::intValue)
//                .collect(Collectors.toList());

        Integer[] imageIdsArray = (Integer[]) tuple.get("imageIds");
        List<Integer> imageIds = Arrays.asList(imageIdsArray);

        String categoryName = (String) tuple.get("categoryName");

//        cockroachDb
//        Integer categoryId = tuple.get("categoryId") != null ? ((Long) tuple.get("categoryId")).intValue() : null;
        Integer categoryId = tuple.get("categoryId") != null ? ((Integer) tuple.get("categoryId")) : null;
        String categoryDescription = (String) tuple.get("categoryDescription");

        return new ProductWithoutImageDataDTO(productId, productName, description, price, stockQty, discount, updateDate, createDate, imageIds, categoryName, categoryId, categoryDescription);
    }

    @Transactional
    public Boolean update(UpdateProductDTO newProduct) {
        ProductEntity product = productDAO.findById(
                newProduct.getId()).orElseThrow(() ->
                new ProductNotFoundException("product not found"));
        List<Integer> stockQtyList = newProduct.getStockQty();
        Integer[] array = stockQtyList.toArray(new Integer[0]);
        return productDAO.updateProduct(
                product.getId(), newProduct.getName(), newProduct.getPrice(),
                newProduct.getDescription(), newProduct.getDiscount(),
                array
        ) > 0;
    }

    public boolean delete(Integer productId) {
        productDAO.deleteById(productId);
        return productDAO.findById(productId).isEmpty();
    }

    public List<ProductWithoutImageDataDTO> retrieveAllByCategoryId(Integer categoryId) {
        List<Tuple> tuples = productDAO.findAllByCategoryId(categoryId); // Assuming you are fetching data as Tuple
        List<ProductWithoutImageDataDTO> products = new ArrayList<>();

        for (Tuple tuple : tuples) {
            ProductWithoutImageDataDTO productWithoutImageDataDTO = convertToDTO(tuple);
            products.add(productWithoutImageDataDTO);
        }
        return products;
    }

    private List<ProductWithoutImageDataDTO> convertToDTOs(List<Tuple> tuples) {
        return tuples.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
