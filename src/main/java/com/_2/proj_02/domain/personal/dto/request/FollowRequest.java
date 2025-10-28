package com._2.proj_02.domain.personal.dto.request;

import com._2.proj_02.domain.auth.entity.SiteUser;
import com._2.proj_02.domain.auth.entity.Studio;
import lombok.Data;

@Data
public class FollowRequest {

    private SiteUser siteUser;
    private Studio studio;
}