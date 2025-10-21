package com._2.proj_02.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartRequest {

    private Long userId;
    private Long productId;
    private Long quantity;
}