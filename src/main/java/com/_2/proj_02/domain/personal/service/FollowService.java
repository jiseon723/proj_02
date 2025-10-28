package com._2.proj_02.domain.personal.service;

import com._2.proj_02.domain.auth.entity.SiteUser;
import com._2.proj_02.domain.auth.entity.Studio;
import com._2.proj_02.domain.personal.dto.request.FollowRequest;
import com._2.proj_02.domain.personal.dto.response.FollowResponse;
import com._2.proj_02.domain.personal.entity.Follow;
import com._2.proj_02.domain.personal.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {

    private final FollowRepository followRepository;

    // 사용자별 팔로우 목록 조회
    public List<FollowResponse> getFollowsByUserId(SiteUser siteUser) {
        List<Follow> follows = followRepository.findBySiteUser(siteUser);

        return follows.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // 셀러의 팔로워 목록 조회
    public List<FollowResponse> getFollowersBySellerId(Studio studio) {
        List<Follow> followers = followRepository.findByStudio(studio);

        return followers.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // 팔로우 추가
    @Transactional
    public FollowResponse addFollow(FollowRequest request) {
        // 이미 팔로우 중인지 확인
        Optional<Follow> existing = followRepository.findBySiteUserAndStudio(
                request.getSiteUser(), request.getStudio());

        if (existing.isPresent()) {
            throw new IllegalStateException("이미 팔로우 중입니다.");
        }

        Follow follow = Follow.builder()
                .siteUser(SiteUser.builder().id(request.getSiteUser().getId()).build())
                .studio(request.getStudio())
                .build();

        Follow saved = followRepository.save(follow);
        return convertToResponse(saved);
    }

    // 팔로우 취소
    @Transactional
    public void unfollow(SiteUser siteUser, Studio studio) {
        Follow follow = followRepository.findBySiteUserAndStudio(siteUser, studio)
                .orElseThrow(() -> new IllegalArgumentException("팔로우 정보를 찾을 수 없습니다."));

        followRepository.delete(follow);
    }

    // 팔로우 여부 확인
    public boolean isFollowing(SiteUser siteUser, Studio studio) {
        return followRepository.existsBySiteUser_AndStudio(siteUser, studio);
    }

    // 팔로워 수 조회
    public long getFollowerCount(Studio studio) {
        return followRepository.countByStudio(studio);
    }

    // 팔로잉 수 조회
    public long getFollowingCount(SiteUser siteUser) {
        return followRepository.countBySiteUser(siteUser);
    }

    // Entity -> Response DTO 변환
    private FollowResponse convertToResponse(Follow follow) {
        return FollowResponse.builder()
                .followId(follow.getFollowId())
                .siteUser(follow.getSiteUser())
                .studio(follow.getStudio())
                .sellerName("판매자명") // TODO: Seller 엔티티에서 가져오기
                .createdAt(follow.getCreatedAt())
                .build();
    }
}