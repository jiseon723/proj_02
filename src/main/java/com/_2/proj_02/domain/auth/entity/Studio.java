package com._2.proj_02.domain.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Studio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long studioId;

    @ManyToOne
    private SiteUser siteUser;

    @Column(length = 150)
    private String studioName;

    @Column(columnDefinition = "TEXT")
    private String studioDescription;

    @Column(length = 10)
    private String studioMobile;

    @Column(length = 10)
    private String studioOfficeTell;

    @Column(length = 10)
    private String studioFax;

    @Column(length = 10)
    private String studioEmail;

    @Column(length = 15)
    private String studioBusinessNumber;

    @Column(length = 10)
    private String studioAddPostNumber;

    @Column(length = 254)
    private String studioAddMain;

    @Column(length = 254)
    private String studioAddDetail;

    /*카테고리 외래키 카테고리 테이블 생성시 연결*/
    @Column(length = 20)
    private Long categoryId;

    @Column(length = 254)
    private String studioImg;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

}
