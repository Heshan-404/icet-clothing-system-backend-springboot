package com.clothify.ecommerce.dao.product;

import com.clothify.ecommerce.dao.product.image.JpaProductImageDAO;
import com.clothify.ecommerce.entity.product.ProductEntity;
import com.clothify.ecommerce.entity.product.image.ProductImageEntity;
import com.clothify.ecommerce.exception.NotFoundException;
import com.clothify.ecommerce.exception.RuntimePersistException;
import lombok.RequiredArgsConstructor;
import org.postgresql.jdbc.PgArray;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Primary
@RequiredArgsConstructor
public class ProductDAOImpl implements ProductDAO {

    private final JpaProductDAO jpaProductDAO;
    private final JpaProductImageDAO jpaProductImageDAO;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public ProductEntity retrieveProductById(Integer productId) {
        Optional<ProductEntity> byId = jpaProductDAO.findById(productId);
        if (byId.isPresent()) return byId.get();
        throw new NotFoundException("Product Not Found");
    }

    @Override
    public List<ProductEntity> retrieveProductList() {
        List<ProductEntity> all = (List<ProductEntity>) jpaProductDAO.findAll();
        if (all.isEmpty()) throw new NotFoundException("Product List Not Found");
        return all;
    }

    @Override
    public Integer persistImage(ProductImageEntity productImageEntity) {
        ProductImageEntity savedImage = jpaProductImageDAO.save(productImageEntity);
        return savedImage.getImageId();
    }

    @Override
    public ProductImageEntity getImageById(Integer imageId) {
        Optional<ProductImageEntity> byId = jpaProductImageDAO.findById(imageId);
        if (byId.isPresent()) return byId.get();
        throw new NotFoundException("Image Not Found");
    }

    @Override
    public void persist(ProductEntity product) {
        ProductEntity save = jpaProductDAO.save(product);
        if (save.getProductId() == null) throw new RuntimePersistException("Product Not Persisted");
    }

    @Override
    public List<ProductEntity> retrieveProductListByCategoryId(Integer categoryId) {
        String sql = "SELECT * FROM product WHERE category_id = " + categoryId;
        List<ProductEntity> productEntityList = new ArrayList<>();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        for (Map m : maps) {
            try {
                ProductEntity build = ProductEntity.builder()
                        .name(m.get("name").toString())
                        .price(Double.valueOf(m.get("price").toString()))
                        .discount(Double.valueOf(m.get("discount").toString()))
                        .productId(Integer.valueOf(m.get("product_id").toString()))
                        .categoryId(Integer.valueOf(m.get("category_id").toString()))
                        .createdDate(LocalDate.parse(m.get("created_date").toString()))
                        .updatedDate(LocalDate.parse(m.get("updated_date").toString()))
                        .description(m.get("description").toString())
                        // CockRoach DB
//                        .imageIdList(Arrays.stream((Long[]) ((PgArray) m.get("image_id_list")).getArray())
//                                .map(Long::intValue)
//                                .collect(Collectors.toList()))
//                        .productSizesCount(Arrays.stream((Long[]) ((PgArray) m.get("product_sizes_count")).getArray())
//                                .map(Long::intValue)
//                                .collect(Collectors.toList()))
//                        .build();

                        //localhost postgreSQL
                        .imageIdList(Arrays.asList((Integer[]) ((PgArray) m.get("image_id_list")).getArray()))
                        .productSizesCount(Arrays.asList((Integer[]) ((PgArray) m.get("product_sizes_count")).getArray()))
                        .build();
                productEntityList.add(build);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (productEntityList.isEmpty()) throw new NotFoundException("Not found any product in this category");
        return productEntityList;
    }

    @Override
    public void udpate(ProductEntity productEntity) {
        ProductEntity save = jpaProductDAO.save(productEntity);
        if (save.getProductId() == null) throw new RuntimePersistException("Product Not Updated");
    }

    @Override
    public void deleteProduct(Integer productId) {
        jpaProductDAO.deleteById(productId);
    }
}

