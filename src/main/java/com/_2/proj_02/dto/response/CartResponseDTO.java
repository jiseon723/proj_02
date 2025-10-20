package com._2.proj_02.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDTO {
    private Long cartId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Integer price;
    private Boolean selected;
}
