package com._2.proj_02.domain.personal.service;

import com._2.proj_02.domain.auth.entity.SiteUser;
import com._2.proj_02.domain.personal.dto.request.WishListRequest;
import com._2.proj_02.domain.personal.dto.response.WishListResponse;
import com._2.proj_02.domain.personal.entity.WishList;
import com._2.proj_02.domain.personal.repository.WishListRepository;
import com._2.proj_02.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishListService {

    private final WishListRepository wishListRepository;

    // 사용자별 찜목록 조회
    public List<WishListResponse> getWishListByUser(SiteUser siteUser) {
        List<WishList> wishLists = wishListRepository.findBySiteUser(siteUser);

        return wishLists.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // 찜 추가
    @Transactional
    public WishListResponse addWishList(WishListRequest request) {
        // 이미 찜한 상품인지 확인
        Optional<WishList> existing = wishListRepository.findBySiteUserAndProduct(
                request.getSiteUser(), request.getProduct());

        if (existing.isPresent()) {
            throw new IllegalStateException("이미 찜한 상품입니다.");
        }

        WishList wishList = WishList.builder()
                .siteUser(SiteUser.builder().id(request.getSiteUser().getId()).build())
                .product(request.getProduct())
                .build();

        WishList saved = wishListRepository.save(wishList);
        return convertToResponse(saved);
    }

    // 찜 삭제
    @Transactional
    public void removeWishList(Long wishlistId) {
        WishList wishList = wishListRepository.findById(wishlistId)
                .orElseThrow(() -> new IllegalArgumentException("찜 정보를 찾을 수 없습니다."));

        wishListRepository.delete(wishList);
    }

    // 찜 삭제 (사용자 + 상품)
    @Transactional
    public void removeWishListByUserAndProduct(SiteUser siteUser, Product product) {
        WishList wishList = wishListRepository.findBySiteUserAndProduct(siteUser, product)
                .orElseThrow(() -> new IllegalArgumentException("찜 정보를 찾을 수 없습니다."));

        wishListRepository.delete(wishList);
    }

    // 찜 여부 확인
    public boolean isWished(SiteUser siteUser, Product product) {
        return wishListRepository.existsBySiteUserAndProduct(siteUser, product);
    }

    // 상품의 찜 개수 조회
    public long getWishCount(Product product) {
        return wishListRepository.countByProduct(product);
    }

    // 사용자의 찜 개수 조회
    public long getUserWishCount(SiteUser siteUser) {
        return wishListRepository.countBySiteUser(siteUser);
    }

    // Entity -> Response DTO 변환
    private WishListResponse convertToResponse(WishList wishList) {
        return WishListResponse.builder()
                .wishlistId(wishList.getWishlistId())
                .siteUser(wishList.getSiteUser())
                .product(wishList.getProduct())
                .productName("상품명") // TODO: Product 엔티티에서 가져오기
                .createdAt(wishList.getCreatedAt())
                .build();
    }
}