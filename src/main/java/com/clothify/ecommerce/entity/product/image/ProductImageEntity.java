package com.clothify.ecommerce.entity.product.image;

import jakarta.persistence.*;
import lombok.*;

@Data
@ToString
@AllArgsConstructor
@Entity
@Table(name = "product_image")
@NoArgsConstructor
@Builder
public class ProductImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "image_id")
    private Integer imageId;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "image_type")
    private String imageType;

    @Column(name = "image_data", columnDefinition = "BYTEA")
    private byte[] imageData;
}
