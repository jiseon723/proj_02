package com._2.proj_02.domain.product.dto.response;

import com._2.proj_02.domain.product.dto.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CategoryResponse {
    private final List<CategoryDto> categoryList;
}
