package com.clothify.ecommerce.dto.product;

import com.clothify.ecommerce.Views;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.Date;
import java.util.List;

public interface ProductWithoutImageData {
    @JsonView(Views.Public.class)
    Integer getProductId();
    @JsonView(Views.Public.class)
    String getProductName();
    @JsonView(Views.Public.class)
    String getDescription();
    @JsonView(Views.Public.class)
    Double getPrice();
    @JsonView(Views.Public.class)
    @JsonProperty("stockQty")
    List<Integer> getStockQty();
    @JsonView(Views.Public.class)
    Double getDiscount();
    @JsonView(Views.Public.class)
    Date getUpdateDate();
    @JsonView(Views.Public.class)
    Date getCreateDate();
    @JsonView(Views.Public.class)
    List<Integer> getImageIds();
    @JsonView(Views.Public.class)
    String getCategoryName();
    @JsonView(Views.Public.class)
    Integer getCategoryId();
    @JsonView(Views.Public.class)
    String getCategoryDescription();

}
