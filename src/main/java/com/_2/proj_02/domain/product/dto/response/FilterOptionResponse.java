package com._2.proj_02.domain.product.dto.response;

import com._2.proj_02.domain.product.dto.FilterOptionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FilterOptionResponse {
    private final List<FilterOptionDto> filterOptionList;
}
