package com.clothify.ecommerce.dto.product.image;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageDTO {
    private Integer id;
    private String name;
    private byte[] data;
}
