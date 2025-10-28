package com._2.proj_02.domain.auth.dto;

import com._2.proj_02.domain.auth.entity.SiteUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class SiteUserDto {

    private Long id;
    private String userName;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public SiteUserDto(SiteUser siteUser){
        this.id = siteUser.getId();
        this.userName = siteUser.getUserName();
        this.createdDate = siteUser.getCreatedDate();
        this.updatedDate = siteUser.getUpdatedDate();
    }

}


