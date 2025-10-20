package com._2.proj_02.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodResponseDTO {
    private Long paymentId;
    private String type;
    private String cardNumber;
    private String cardCompany;
    private String expiryDate;
    private String holderName;
}
