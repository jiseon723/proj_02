package com._2.proj_02.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailDto {
    private Long orderId;
    private String orderNumber;
    private List<OrderItemDto> items;
    private Integer totalAmount;
    private String paymentMethod;
    private LocalDateTime createdAt;
}