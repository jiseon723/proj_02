package com._2.proj_02.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDTO {
    private Long userId;
    private String name;
    private String phone;
    private String email;
}
