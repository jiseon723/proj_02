package com._2.proj_02.domain.review.repository;

import com._2.proj_02.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
