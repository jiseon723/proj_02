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
public class FollowResponseDto {
    private Long sellerId;
    private String sellerName;
    private String category;
    private String profileImage;
    private LocalDateTime followedAt;
}