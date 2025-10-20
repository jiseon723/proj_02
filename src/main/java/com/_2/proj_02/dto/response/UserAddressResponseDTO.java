package com._2.proj_02.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressResponseDTO {
    private Long addressId;
    private String recipient;
    private String phone;
    private String zipCode;
    private String address;
    private String detailAddress;
    private Boolean isDefault;
}
