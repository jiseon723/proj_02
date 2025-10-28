package com._2.proj_02.domain.personal.dto.request;

import com._2.proj_02.domain.product.entity.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemRequest {
    private Product product;
    private Long quantity;
    private BigDecimal price;
}