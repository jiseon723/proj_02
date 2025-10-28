package com._2.proj_02.domain.product.productList.controller;

import com._2.proj_02.domain.product.dto.ProductDto;
import com._2.proj_02.domain.product.dto.response.ProductResponse;
import com._2.proj_02.domain.product.productList.service.ProductService;
import com._2.proj_02.global.RsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{subCategoryId}")
    @Operation(summary = "상품 다건 조회")
    public RsData<ProductResponse> categoryList(@PathVariable Long subCategoryId, @RequestParam(defaultValue = "10") int size) {
        List<ProductDto> productList = productService.getProductList(subCategoryId, size);
        return RsData.of("200", "상품 다건 조회 성공", new ProductResponse(productList));
    }


}
