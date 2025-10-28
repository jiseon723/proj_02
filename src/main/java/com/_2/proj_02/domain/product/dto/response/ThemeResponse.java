package com._2.proj_02.domain.product.dto.response;

import com._2.proj_02.domain.product.dto.ThemeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ThemeResponse {
    private final List<ThemeDto> themeList;
}
