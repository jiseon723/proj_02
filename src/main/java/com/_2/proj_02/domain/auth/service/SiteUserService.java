package com._2.proj_02.domain.auth.service;

import com._2.proj_02.domain.auth.dto.request.SignupUserRequest;
import com._2.proj_02.domain.auth.entity.RoleType;
import com._2.proj_02.domain.auth.entity.SiteUser;
import com._2.proj_02.domain.auth.repository.SiteUserRepository;
import com._2.proj_02.global.RsData.RsData;
import com._2.proj_02.global.config.SecurityUser;
import com._2.proj_02.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class SiteUserService {
    private final SiteUserRepository siteUserRepository;
    private final JwtProvider jwtProvider;


    public SiteUser signupUser(SignupUserRequest signupUserRequest){

        SiteUser checkedSiteUser = siteUserRepository.findByEmail(signupUserRequest.getEmail());
        if(checkedSiteUser != null){
            throw new RuntimeException("이미 가입된 사용자입니다.");
        }
        SiteUser newUser = SiteUser.builder()
                .email(signupUserRequest.getEmail())
                .password(signupUserRequest.getPassword())
                .userName(signupUserRequest.getUserName())
                .mobilePhone(signupUserRequest.getMobilePhone())
                .nickName(signupUserRequest.getNickName())
                .role(RoleType.USER)
                .status(signupUserRequest.getStatus())
                .gender(signupUserRequest.getGender())
                //.profileImg(signupUserRequest.getProfileImg())
                .birth(signupUserRequest.getBirth())
                .createdDate(LocalDateTime.now())
                .build();

        String refreshToken = jwtProvider.genRefreshToken(newUser);
        newUser.setRefreshToken(refreshToken);

        siteUserRepository.save(newUser);

        return newUser;
    }

    public SiteUser getSiteUser(String email) {
        return siteUserRepository.findByEmail(email);
    }
    public boolean validateToken(String accessToken) {
        return jwtProvider.verify(accessToken);
    }
    public RsData<String> refreshAccessToken(String refreshToken) {
        SiteUser siteUser = siteUserRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new RuntimeException("존재하지 않는 리프레시 토큰입니다."));

        String accessToken = jwtProvider.genAccessToken(siteUser);

        return RsData.of("200", "토큰 갱신 성공", accessToken);
    }
    public SecurityUser getUserFromAccessToken(String accessToken) {
        Map<String, Object> payloadBody = jwtProvider.getClaims(accessToken);

        long id = (int) payloadBody.get("id");
        String username = (String) payloadBody.get("username");
        List<GrantedAuthority> authorities = new ArrayList<>();

        return new SecurityUser(id, username, "", authorities);

    }
    /*
        Member checkedMember = memberRepository.findByUsername(username);
        if(checkedMember != null){
            throw new RuntimeException("이미 가입된 사용자입니다.");
        }
        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();

        String refreshToken = jwtProvider.genRefreshToken(member);
        member.setRefreshToken(refreshToken);

        memberRepository.save(member);
        return member;
    */
}
