package com._2.proj_02.domain.personal.dto.request;

import lombok.*;

@Data
public class DeliveryRequest {

    private Long deliveryId;
    private String trackingNumber;
    private String deliveryStatus;
}
