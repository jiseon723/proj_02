package com._2.proj_02.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryUpdateRequest {

    private Long deliveryId;
    private String trackingNumber;
    private String deliveryStatus;
}
