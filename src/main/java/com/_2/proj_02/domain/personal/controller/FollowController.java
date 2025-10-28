package com._2.proj_02.domain.personal.controller;

import com._2.proj_02.domain.auth.entity.SiteUser;
import com._2.proj_02.domain.auth.entity.Studio;
import com._2.proj_02.domain.personal.dto.request.FollowRequest;
import com._2.proj_02.domain.personal.dto.response.FollowResponse;
import com._2.proj_02.domain.personal.service.FollowService;
import com._2.proj_02.global.RsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/mypage/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    // 팔로우 목록 페이지
    @GetMapping
    @Operation(summary = "팔로우 다건 조회")
    public RsData<List<FollowResponse>> followList(@RequestParam(required = false) SiteUser siteUser) {
        if (siteUser == null) {
            return RsData.of("400", "유저 정보가 없습니다."); // 테스트용 기본값
        }

        List<FollowResponse> follows = followService.getFollowsByUserId(siteUser);

        return RsData.of("200", "팔로우 다건 조회 성공", follows);
    }

    // 팔로우 추가
    @PostMapping
    @ResponseBody
    @Operation(summary = "팔로우 추가")
    public RsData<FollowResponse> addFollow(@RequestBody FollowRequest request) {
        FollowResponse response = followService.addFollow(request);
        return RsData.of("200", "팔로우 추가 성공", response);
    }

    // 팔로우 취소
    @DeleteMapping
    @ResponseBody
    @Operation(summary = "팔로우 취소")
    public RsData<String> unfollow(@RequestParam SiteUser siteUser, @RequestParam Studio studio) {
        followService.unfollow(siteUser, studio);
        return RsData.of("200", "삭제성공");
    }

    // 팔로우 여부 확인
    @GetMapping("/check")
    @ResponseBody
    @Operation(summary = "팔로우 여부 확인")
    public RsData<Boolean> isFollowing(
            @RequestParam SiteUser siteUser,
            @RequestParam Studio studio) {
        boolean isFollowing = followService.isFollowing(siteUser, studio);
        return RsData.of("200", "팔로우 여부 확인 성공", isFollowing);
    }

    // 셀러의 팔로워 수 조회
    @GetMapping("/count/followers")
    @ResponseBody
    @Operation(summary = "팔로우 여부 확인")
    public RsData<Long> getFollowerCount(@RequestParam Studio studio) {
        long count = followService.getFollowerCount(studio);
        return RsData.of("200", "팔로우 여부 확인 성공", count);
    }

    // 사용자의 팔로잉 수 조회
    @GetMapping("/count/following")
    @ResponseBody
    @Operation(summary = "사용자의 팔로잉 수 조회")
    public RsData<Long> getFollowingCount(@RequestParam SiteUser siteUser) {
        long count = followService.getFollowingCount(siteUser);
        return RsData.of("200", "사용자의 팔로잉 수 조회", count);
    }
}