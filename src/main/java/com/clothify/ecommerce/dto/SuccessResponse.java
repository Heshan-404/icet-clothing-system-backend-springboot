package com.clothify.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SuccessResponse {
    private String msg;
    private Integer code;
}
