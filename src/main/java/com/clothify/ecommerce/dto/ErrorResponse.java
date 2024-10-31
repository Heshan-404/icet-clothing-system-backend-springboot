package com.clothify.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {
    private String msg;
    private Integer code;
}
