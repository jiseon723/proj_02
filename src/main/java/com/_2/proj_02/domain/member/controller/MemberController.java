package com._2.proj_02.domain.member.controller;

import com._2.proj_02.domain.member.dto.MemberResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    @GetMapping("/sample")
    public MemberResponseDto sample() {
        return new MemberResponseDto(1L, "testuser");
    }
}
