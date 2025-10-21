package com._2.proj_02.domain.mypage.dto.request;

import com._2.proj_02.domain.auth.entity.Studio;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowRequest {

    private Long userId;
    private Long sellerId;
    private Studio seller;
}