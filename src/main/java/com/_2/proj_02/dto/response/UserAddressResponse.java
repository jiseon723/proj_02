package com._2.proj_02.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAddressResponse {

    private Long userAddressId;
    private Long userId;
    private String recipientName;
    private String baseAddress;
    private String detailAddress;
    private String zipcode;
    private Boolean isDefault;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}