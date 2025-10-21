package com._2.proj_02.domain.mypage.dto.request;

import com._2.proj_02.domain.product.entity.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartRequest {

    private Long userId;
    private Long productId;
    private Product product;
    private Long quantity;
}