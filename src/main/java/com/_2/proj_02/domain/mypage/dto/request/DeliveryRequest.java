package com._2.proj_02.domain.mypage.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryRequest {

    private Long deliveryId;
    private String trackingNumber;
    private String deliveryStatus;
}
