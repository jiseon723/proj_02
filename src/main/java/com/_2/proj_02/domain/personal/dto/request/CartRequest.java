package com._2.proj_02.domain.personal.dto.request;

import com._2.proj_02.domain.auth.entity.SiteUser;
import com._2.proj_02.domain.product.entity.Product;
import lombok.Data;

@Data
public class CartRequest {

    private SiteUser siteUser;
    private Product product;
    private Long quantity;
}