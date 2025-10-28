package com._2.proj_02.domain.personal.dto.request;

import com._2.proj_02.domain.auth.entity.SiteUser;
import lombok.Data;

@Data
public class UserAddressRequest {

    private SiteUser siteUser;
    private String recipientName;
    private String baseAddress;
    private String detailAddress;
    private String zipcode;
    private Boolean isDefault;
}
