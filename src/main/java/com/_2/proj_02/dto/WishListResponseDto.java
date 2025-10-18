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
public class WishListResponseDto {
    private Long wishlistId;
    private Long productId;
    private String productName;
    private String productImage;
    private Integer price;
    private Boolean inStock;
    private LocalDateTime createdAt;
}