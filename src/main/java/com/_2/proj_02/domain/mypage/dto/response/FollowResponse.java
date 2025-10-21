package com._2.proj_02.domain.mypage.dto.response;

import com._2.proj_02.domain.auth.entity.Studio;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowResponse {

    private Long followId;
    private Long userId;
    private Long studioId;
    private Studio seller;
    private String sellerName; // Seller 엔티티에서 가져올 예정
    private LocalDateTime createdAt;
}