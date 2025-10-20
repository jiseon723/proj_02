package com._2.proj_02.service;

import com._2.proj_02.repository.FollowRepository;
import com._2.proj_02.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WishlistService {

    private final FollowRepository followRepository;
    private final WishlistRepository wishlistRepository;

    public List<FollowResponseDTO> getFollowList(String category) {
        return followRepository.findByCategory(category)
                .stream()
                .map(FollowResponseDTO::new)
                .collect(Collectors.toList());
    }

    public void unfollowSeller(Long sellerId) {
        followRepository.deleteById(sellerId);
    }

    public List<WishlistResponseDTO> getWishlist(String category) {
        return wishlistRepository.findByCategory(category)
                .stream()
                .map(WishlistResponseDTO::new)
                .collect(Collectors.toList());
    }

    public void deleteWishlist(Long wishlistId) {
        wishlistRepository.deleteById(wishlistId);
    }
}