package com._2.proj_02.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryDetailDto {
    private Long orderId;
    private String orderNumber;
    private String trackingNumber;
    private String courierCompany;
    private String status;
    private List<String> trackingHistory;
}