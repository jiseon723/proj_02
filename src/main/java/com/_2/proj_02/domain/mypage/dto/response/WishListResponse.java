package com._2.proj_02.domain.mypage.dto.response;

import com._2.proj_02.domain.product.entity.Product;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishListResponse {

    private Long wishlistId;
    private Long userId;
    private Long productId;
    private Product product;
    private String productName; // Product 엔티티에서 가져올 예정
    private LocalDateTime createdAt;
}