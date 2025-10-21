package com._2.proj_02.domain.mypage.service;

import com._2.proj_02.domain.auth.entity.SiteUser;
import com._2.proj_02.domain.mypage.dto.request.UserAddressRequest;
import com._2.proj_02.domain.mypage.dto.response.UserAddressResponse;
import com._2.proj_02.domain.mypage.entity.UserAddress;
import com._2.proj_02.domain.mypage.repository.UserAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAddressService {

    private final UserAddressRepository userAddressRepository;

    // 사용자별 배송지 목록 조회
    public List<UserAddressResponse> getAddressesByUserId(Long userId) {
        List<UserAddress> addresses = userAddressRepository.findByUser_UserId(userId);

        return addresses.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // 배송지 등록
    @Transactional
    public UserAddressResponse createAddress(UserAddressRequest request) {
        // 기본 배송지로 설정하는 경우, 기존 기본 배송지 해제
        if (request.getIsDefault() != null && request.getIsDefault()) {
            userAddressRepository.unsetDefaultByUserId(request.getUserId());
        }

        UserAddress address = UserAddress.builder()
                .user(SiteUser.builder().id(request.getUserId()).build())
                .recipientName(request.getRecipientName())
                .baseAddress(request.getBaseAddress())
                .detailAddress(request.getDetailAddress())
                .zipcode(request.getZipcode())
                .isDefault(request.getIsDefault() != null ? request.getIsDefault() : false)
                .build();

        UserAddress savedAddress = userAddressRepository.save(address);
        return convertToResponse(savedAddress);
    }

    // 배송지 수정
    @Transactional
    public UserAddressResponse updateAddress(Long addressId, UserAddressRequest request) {
        UserAddress address = userAddressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("배송지를 찾을 수 없습니다."));

        // 기본 배송지로 변경하는 경우
        if (request.getIsDefault() != null && request.getIsDefault() && !address.getIsDefault()) {
            userAddressRepository.unsetDefaultByUserId(address.getUser().getId());
        }

        address.setRecipientName(request.getRecipientName());
        address.setBaseAddress(request.getBaseAddress());
        address.setDetailAddress(request.getDetailAddress());
        address.setZipcode(request.getZipcode());

        if (request.getIsDefault() != null) {
            address.setIsDefault(request.getIsDefault());
        }

        return convertToResponse(address);
    }

    // 배송지 삭제
    @Transactional
    public void deleteAddress(Long addressId) {
        UserAddress address = userAddressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("배송지를 찾을 수 없습니다."));

        userAddressRepository.delete(address);
    }

    // 기본 배송지 설정
    @Transactional
    public void setDefaultAddress(Long addressId, Long userId) {
        UserAddress address = userAddressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("배송지를 찾을 수 없습니다."));

        // 기존 기본 배송지 해제
        userAddressRepository.unsetDefaultByUserId(userId);

        // 새로운 기본 배송지 설정
        address.setIsDefault(true);
    }

    // Entity -> Response DTO 변환
    private UserAddressResponse convertToResponse(UserAddress address) {
        return UserAddressResponse.builder()
                .userAddressId(address.getUserAddressId())
                .userId(address.getUser().getId())
                .recipientName(address.getRecipientName())
                .baseAddress(address.getBaseAddress())
                .detailAddress(address.getDetailAddress())
                .zipcode(address.getZipcode())
                .isDefault(address.getIsDefault())
                .createdAt(address.getCreatedAt())
                .updatedAt(address.getUpdatedAt())
                .build();
    }
}