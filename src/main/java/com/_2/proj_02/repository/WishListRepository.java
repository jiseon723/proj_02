package com._2.proj_02.repository;

import com._2.proj_02.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {

    // 사용자별 찜목록 조회
    List<WishList> findByUser_UserId(Long userId);

    // 사용자와 상품으로 찜목록 조회
    Optional<WishList> findByUser_UserIdAndProductId(Long userId, Long productId);

    // 상품별 찜 개수
    long countByProductId(Long productId);

    // 사용자별 찜 개수
    long countByUser_UserId(Long userId);

    // 찜 여부 확인
    boolean existsByUser_UserIdAndProductId(Long userId, Long productId);
}