package com._2.proj_02.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponseDto {
    private Long userId;
    private String nickname;
    private String email;
    private String phoneNumber;
    private LocalDateTime createdAt;
}
