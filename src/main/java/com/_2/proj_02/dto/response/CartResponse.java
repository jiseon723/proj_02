package com._2.proj_02.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {

    private Long cartId;
    private Long userId;
    private Long productId;
    private String productName; // Product 엔티티에서 가져올 예정
    private Long quantity;
    private LocalDateTime createdAt;
}