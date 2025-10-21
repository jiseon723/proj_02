package com._2.proj_02.domain.review.repository;

import com._2.proj_02.domain.review.entity.ReviewComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewCommentRepository extends JpaRepository<ReviewComment, Long> {

}
