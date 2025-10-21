package com._2.proj_02.domain.auth.repository;

import com._2.proj_02.domain.auth.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SiteUserRepository extends JpaRepository<SiteUser, Long> {
    SiteUser findByEmail(String email);
    Optional<SiteUser> findByRefreshToken(String refreshToken);
}
