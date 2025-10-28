package com._2.proj_02.domain.personal.dto.request;

import com._2.proj_02.domain.auth.entity.SiteUser;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrdersRequest {

    private SiteUser siteUser;
    private Long deliveryAddressId;
    private BigDecimal totalPrice;
    private List<OrderItemRequest> orderItems;
}
