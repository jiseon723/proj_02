package com._2.proj_02.domain.mypage.service;

import com._2.proj_02.domain.auth.entity.SiteUser;
import com._2.proj_02.domain.mypage.dto.request.CartRequest;
import com._2.proj_02.domain.mypage.dto.response.CartResponse;
import com._2.proj_02.domain.mypage.entity.Cart;
import com._2.proj_02.domain.mypage.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;

    // 사용자별 장바구니 목록 조회
    public List<CartResponse> getCartsByUserId(Long userId) {
        List<Cart> carts = cartRepository.findByUser_Id(userId);

        return carts.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // 장바구니 담기
    @Transactional
    public CartResponse addToCart(CartRequest request) {
        // 이미 장바구니에 있는 상품인지 확인
        Optional<Cart> existingCart = cartRepository.findByUser_IdAndProduct_Id(
                request.getUserId(), request.getProductId());

        if (existingCart.isPresent()) {
            // 이미 있으면 수량 증가
            Cart cart = existingCart.get();
            cart.setQuantity(cart.getQuantity() + request.getQuantity());
            return convertToResponse(cart);
        } else {
            // 없으면 새로 추가
            Cart cart = Cart.builder()
                    .user(SiteUser.builder().id(request.getUserId()).build())
                    //.product(request.getProductId())
                    .product(request.getProduct())
                    .quantity(request.getQuantity())
                    .build();

            Cart saved = cartRepository.save(cart);
            return convertToResponse(saved);
        }
    }

    // 장바구니 수량 수정
    @Transactional
    public CartResponse updateCartQuantity(Long cartId, Long quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 항목을 찾을 수 없습니다."));

        cart.setQuantity(quantity);
        return convertToResponse(cart);
    }

    // 장바구니 항목 삭제
    @Transactional
    public void deleteCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 항목을 찾을 수 없습니다."));

        cartRepository.delete(cart);
    }

    // 장바구니 전체 삭제
    @Transactional
    public void clearCart(Long userId) {
        cartRepository.deleteByUser_Id(userId);
    }

    // 장바구니 개수 조회
    public long getCartCount(Long userId) {
        return cartRepository.countByUser_Id(userId);
    }

    // Entity -> Response DTO 변환
    private CartResponse convertToResponse(Cart cart) {
        return CartResponse.builder()
                .cartId(cart.getCartId())
                //.userId(cart.getUser().getUserId())
                .userId(cart.getUser().getId())
                //.productId(cart.getProductId())
                .product(cart.getProduct())
                .productName("상품명") // TODO: Product 엔티티에서 가져오기
                .quantity(cart.getQuantity())
                .createdAt(cart.getCreatedAt())
                .build();
    }
}