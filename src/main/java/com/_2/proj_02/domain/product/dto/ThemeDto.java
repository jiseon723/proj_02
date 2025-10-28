package com._2.proj_02.domain.product.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ThemeDto {
    private Long id;
    private String name;
    private String code;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

}