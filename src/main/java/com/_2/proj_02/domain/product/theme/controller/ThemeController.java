package com._2.proj_02.domain.product.theme.controller;

import com._2.proj_02.domain.product.dto.ThemeDto;
import com._2.proj_02.domain.product.dto.response.ThemeResponse;
import com._2.proj_02.domain.product.theme.service.ThemeService;
import com._2.proj_02.global.RsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/theme")
public class ThemeController {

    private final ThemeService themeService;

    @GetMapping("")
    @Operation(summary = "테마 다건 조회")
    public RsData<ThemeResponse> themeList(@RequestParam(defaultValue = "4") int size) {
        List<ThemeDto> themeList = themeService.getThemeList(size);
        return RsData.of("200", "테마 다건 조회 성공", new ThemeResponse(themeList));
    }
}
