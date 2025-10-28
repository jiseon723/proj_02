package com._2.proj_02.domain.personal.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SiteUserUpdateRequest {
    private String password;
    private String email;
    private String mobilePhone;
}