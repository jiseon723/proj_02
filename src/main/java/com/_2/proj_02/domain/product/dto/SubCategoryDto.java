package com._2.proj_02.domain.product.dto;

import com._2.proj_02.domain.product.entity.Subcategory;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryDto {
    private Long id;
    private String name;
    private String code;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public SubCategoryDto(Subcategory subcategory) {
        this.id = subcategory.getId();
        this.name = subcategory.getName();
        this.code = subcategory.getCode();
        this.createdDate = subcategory.getCreatedDate();
        this.modifiedDate = subcategory.getModifiedDate();
    }

}