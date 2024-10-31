package com.clothify.ecommerce.dto.product.image;

import jakarta.persistence.Lob;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductImageDTO {
    private Integer imageId;
    private String imageName;
    private String imageType;
    @Lob
    private byte[] imageData;

    public ProductImageDTO(String imageName, String imageType, byte[] imageData) {
        this.imageName = imageName;
        this.imageType = imageType;
        this.imageData = imageData;
    }
}
