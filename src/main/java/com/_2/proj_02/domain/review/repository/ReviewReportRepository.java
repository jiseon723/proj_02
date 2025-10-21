package com._2.proj_02.domain.review.repository;

import com.gobang.gobang.domain.review.entity.ReviewReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewReportRepository extends JpaRepository<ReviewReport,Long> {
}
