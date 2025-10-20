package com._2.proj_02.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryResponseDTO {
    private Long deliveryId;
    private String status;
    private String recipient;
    private String address;
    private String detailAddress;
    private String deliveryMessage;
}
