package com._2.proj_02.domain.review.service;

import com._2.proj_02.domain.review.dto.ReviewDto;
import com._2.proj_02.domain.review.entity.Review;
import com._2.proj_02.domain.review.repository.ReviewRepository;
import com._2.proj_02.global.RsData.RsData;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // 리뷰 다건 조회
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // 리뷰 단건 조회
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    // 리뷰 등록
    @Transactional
    public RsData<Review> createReview(ReviewDto.ReviewCreateRequest dto) {
        Review review = Review.builder()
                .orderId(dto.getOrderId())
                .orderItemId(dto.getOrderItemId())
                .productId(dto.getProductId())
                .userId(dto.getUserId())
                .rating(dto.getRating())
                .content(dto.getContent())
                .isActive(true)
                .reviewLike(0)
                .viewCount(0)
                .build();

        reviewRepository.save(review);

        return RsData.of("200","리뷰가 등록되었습니다.", review);
    }

    public Optional<Review> findById(Long id) {

        return reviewRepository.findById(id);
    }

    @Transactional
    public RsData<Review> modify(Review review, @NotNull Integer rating, @NotBlank String content) {
        review.setRating(rating);
        review.setContent(content);

        reviewRepository.save(review);

        return RsData.of(
                "200",
                "%d번 리뷰가 수정되었습니다.".formatted(review.getId()),
                review
        );
    }
//
//    // 리뷰 수정
//    @Transactional
//    public Optional<Review> updateReview(Long id, Review updatedReview) {
//        return reviewRepository.findById(id).map(review -> {
//            review.setContent(updatedReview.getContent());
//            review.setRating(updatedReview.getRating());
//            return reviewRepository.save(review);
//        });
//    }


//    // 리뷰 삭제
//    @Transactional
//    public boolean deleteReview(Long id) {
//        if (reviewRepository.existsById(id)) {
//            reviewRepository.deleteById(id);
//            return true;
//        }
//        return false;
//    }
}