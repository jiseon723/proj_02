package com._2.proj_02.domain.product.homeController;

import com._2.proj_02.domain.product.dto.ThemeDto;
import com._2.proj_02.domain.product.dto.response.ProductResponse;
import com._2.proj_02.domain.product.themaService.ThemeService;
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
@RequestMapping("/api/v1/home")
public class HomeController {

    private final ThemeService themeService;

    @GetMapping("")
    @Operation(summary = "테마 다건 조회")
    public RsData<ProductResponse> themeList(@RequestParam(defaultValue = "4") int size) {
        List<ThemeDto> themeList = themeService.getThemeList(size);
        return RsData.of("200", "테마 다건 조회 성공", new ProductResponse(themeList));
    }
}
