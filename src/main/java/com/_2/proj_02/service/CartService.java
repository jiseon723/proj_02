package com._2.proj_02.service;

import com._2.proj_02.dto.request.CartUpdateDTO;
import com._2.proj_02.dto.response.CartResponseDTO;
import com._2.proj_02.entity.Cart;
import com._2.proj_02.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;

    public List<CartResponseDTO> getCartList() {
        return cartRepository.findAll()
                .stream()
                .map(CartResponseDTO::new)
                .collect(Collectors.toList());
    }

    public void updateCart(Long cartId, CartUpdateDTO dto) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("장바구니 항목 없음"));
        cart.updateFromDTO(dto);
        cartRepository.save(cart);
    }

    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    public void deleteSelectedCart(List<Long> cartIds) {
        cartRepository.deleteAllById(cartIds);
    }
}