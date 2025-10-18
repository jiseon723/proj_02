package com._2.proj_02.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponseDto {
    private Long cartId;
    private Long productId;
    private String productName;
    private String productImage;
    private Long quantity;
    private Long price;
    private Long totalPrice;
    private Boolean inStock;
    private LocalDateTime createdAt;
}
