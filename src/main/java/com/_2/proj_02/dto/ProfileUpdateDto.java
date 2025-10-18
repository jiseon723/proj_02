package com._2.proj_02.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateDto {
    private String nickname;
    private String email;
    private String phoneNumber;
}
