package com._2.proj_02.repository;

import com._2.proj_02.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    List<UserAddress> findByUserId(Long userId);
    Optional<UserAddress> findByUserIdAndIsDefault(Long userId, Boolean isDefault);
    void deleteByUserAddressIdAndUserId(Long userAddressId, Long userId);
}
