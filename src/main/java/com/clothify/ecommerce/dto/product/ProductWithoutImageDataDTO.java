package com.clothify.ecommerce.dto.product;

import java.util.Date;
import java.util.List;


public class ProductWithoutImageDataDTO {

    private Integer productId;
    private String productName;
    private String description;
    private Double price;
    private List<Integer> stockQty;
    private Double discount;
    private Date updateDate;
    private Date createDate;
    private List<Integer> imageIds;
    private String categoryName;
    private Integer categoryId;
    private String categoryDescription;

    // Constructor to match the query result
    public ProductWithoutImageDataDTO(Integer productId, String productName, String description, Double price,
                                      List<Integer> stockQty, Double discount, Date updateDate, Date createDate,
                                      List<Integer> imageIds, String categoryName, Integer categoryId, String categoryDescription) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stockQty = stockQty;
        this.discount = discount;
        this.updateDate = updateDate;
        this.createDate = createDate;
        this.imageIds = imageIds;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.categoryDescription = categoryDescription;
    }

    // Getters and Setters
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Integer> getStockQty() {
        return stockQty;
    }

    public void setStockQty(List<Integer> stockQty) {
        this.stockQty = stockQty;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<Integer> getImageIds() {
        return imageIds;
    }

    public void setImageIds(List<Integer> imageIds) {
        this.imageIds = imageIds;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    @Override
    public String toString() {
        return "ProductWithoutImageDataDTO{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stockQty=" + stockQty +
                ", discount=" + discount +
                ", updateDate=" + updateDate +
                ", createDate=" + createDate +
                ", imageIds=" + imageIds +
                ", categoryName='" + categoryName + '\'' +
                ", categoryId=" + categoryId +
                ", categoryDescription='" + categoryDescription + '\'' +
                '}';
    }
}
