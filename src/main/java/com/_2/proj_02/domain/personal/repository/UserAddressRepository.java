package com._2.proj_02.domain.personal.repository;

import com._2.proj_02.domain.auth.entity.SiteUser;
import com._2.proj_02.domain.personal.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    // 사용자별 배송지 목록 조회
    List<UserAddress> findBySiteUser(SiteUser siteUser);

    // 사용자의 기본 배송지 조회
    Optional<UserAddress> findBySiteUserAndIsDefaultTrue(SiteUser siteUser);

    // 사용자의 모든 배송지를 기본 배송지 해제
    @Modifying
    @Query("UPDATE UserAddress ua SET ua.isDefault = false WHERE ua.siteUser = :siteUser")
    void unsetDefaultBySiteUser(@Param("siteUser") SiteUser siteUser);

    // 사용자별 배송지 개수
    long countBySiteUser(SiteUser siteUser);
}