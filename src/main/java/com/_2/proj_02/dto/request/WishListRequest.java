package com._2.proj_02.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishListRequest {

    private Long userId;
    private Long productId;
}