package com._2.proj_02.domain.personal.dto.response;

import com._2.proj_02.domain.personal.entity.OrderItem;
import com._2.proj_02.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@Builder
public class OrderItemResponse {
    private Long orderItemId;
    private Long orderId;
    private Product product;
    private String productName;
    private Long quantity;
    private BigDecimal price;

    public static OrderItemResponse from(OrderItem orderItem) {
        return OrderItemResponse.builder()
                .orderItemId(orderItem.getOrderItemId())
                .orderId(orderItem.getOrder().getOrderId())
                .product(orderItem.getProduct())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .build();
    }
}