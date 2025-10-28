package com._2.proj_02.domain.personal.dto.response;

import com._2.proj_02.domain.auth.entity.SiteUser;
import com._2.proj_02.domain.personal.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class OrdersResponse {

    private Long orderId;
    private SiteUser siteUser;
    private String orderCord;
    private BigDecimal totalPrice;
    private LocalDateTime createdDate;

    public static OrdersResponse from(Orders orders) {
        return OrdersResponse.builder()
                .orderId(orders.getOrderId())
                .siteUser(orders.getSiteUser())
                .orderCord(orders.getOrderCord())
                .totalPrice(orders.getTotalPrice())
                .build();
    }
}
