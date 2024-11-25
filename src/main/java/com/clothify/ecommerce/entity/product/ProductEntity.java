package com.clothify.ecommerce.entity.product;

import com.clothify.ecommerce.dto.product.ProductDTO;
import com.clothify.ecommerce.entity.product.category.CategoryEntity;
import com.clothify.ecommerce.entity.product.image.ImageEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.*;


@Data
@Entity(name = "product")
@ToString
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Double discount;
    private Date updateDate = new Date(System.currentTimeMillis());
    private Date createdDate = new Date(System.currentTimeMillis());
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Set<ImageEntity> imageList = new HashSet<>();
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
    @JsonProperty("stockQty")
    private List<Integer> stockQty;

    public void update(ProductDTO product) {
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.discount = product.getDiscount();
        this.updateDate = new Date(System.currentTimeMillis());
        this.stockQty = product.getStockQty();
    }
}
