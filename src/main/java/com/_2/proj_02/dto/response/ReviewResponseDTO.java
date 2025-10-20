package com._2.proj_02.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDTO {
    private Long reviewId;
    private Long productId;
    private String productName;
    private String content;
    private Integer rating;
    private String createdAt;
}
