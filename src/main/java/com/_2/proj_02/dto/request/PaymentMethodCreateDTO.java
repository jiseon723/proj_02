package com._2.proj_02.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodCreateDTO {
    private String type;
    private String cardNumber;
    private String cardCompany;
    private String expiryDate;
    private String holderName;
}
