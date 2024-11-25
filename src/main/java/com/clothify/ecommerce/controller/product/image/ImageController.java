package com.clothify.ecommerce.controller.product.image;

import com.clothify.ecommerce.dto.product.image.ImageDTO;
import com.clothify.ecommerce.service.product.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/{imageId}")
    public ImageDTO retrieve(@PathVariable Integer imageId) {
        return imageService.retrieve(imageId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/product/{productId}")
    public ResponseEntity<Object> assignImageToProduct(@RequestParam("image") List<MultipartFile> imageList, @PathVariable Integer productId) {
        List<ImageDTO> imageDTOList = new ArrayList<>();
        imageList.forEach(multipartFile -> {
            try {
                imageDTOList.add(ImageDTO.builder().data(multipartFile.getBytes()).name(multipartFile.getName()).build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        imageService.saveImageToProduct(imageDTOList, productId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Object> removeAllImagesOfAProductByProductId(@PathVariable Integer productId) {
        return Boolean.TRUE.equals(imageService.removeAllByProductId(productId)) ?
                ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{imageId}")
    public ResponseEntity<Object> removeImagesOfAProduct(@PathVariable Integer imageId) {
        return Boolean.TRUE.equals(imageService.removeByImageId(imageId)) ?
                ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }


}
