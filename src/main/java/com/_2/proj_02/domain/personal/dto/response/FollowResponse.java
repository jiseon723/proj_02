package com._2.proj_02.domain.personal.dto.response;

import com._2.proj_02.domain.auth.entity.SiteUser;
import com._2.proj_02.domain.auth.entity.Studio;
import com._2.proj_02.domain.personal.entity.Follow;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class FollowResponse {

    private Long followId;
    private SiteUser siteUser;
    private Studio studio;
    private String sellerName; // Seller 엔티티에서 가져올 예정
    private LocalDateTime createdAt;

    public static FollowResponse from(Follow follow) {
        return FollowResponse.builder()
                .followId(follow.getFollowId())
                .siteUser(follow.getSiteUser())
                .studio(follow.getStudio())
                .createdAt(follow.getCreatedAt())
                .build();
    }
}