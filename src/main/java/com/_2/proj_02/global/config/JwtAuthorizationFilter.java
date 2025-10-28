package com._2.proj_02.global.config;

import com._2.proj_02.domain.auth.entity.SiteUser;
import com._2.proj_02.domain.auth.repository.SiteUserRepository;
import com._2.proj_02.domain.auth.service.SiteUserService;
import com._2.proj_02.global.jwt.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final HttpServletRequest req;
    private final JwtProvider jwtProvider;
    private final SiteUserRepository siteUserRepository;

    private final SiteUserService siteUserService;
    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        if (request.getRequestURI().equals("/api/auth/login/user") || request.getRequestURI().equals("/api/auth/logout") || request.getRequestURI().equals("/api/auth/signup/user")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = _getCookie("accessToken");
        // accessToken 검증 or refreshToken 발급
        /*
        if (!accessToken.isBlank()) {
            // 토큰 유효기간 검증
            if (!siteUserService.validateToken(accessToken)) {
                String refreshToken = _getCookie("refreshToken");

                RsData<String> rs = siteUserService.refreshAccessToken(refreshToken);
                _addHeaderCookie("accessToken", rs.getData());
            }

            // securityUser 가져오기
            SecurityUser securityUser = siteUserService.getUserFromAccessToken(accessToken);
            // 인가 처리
            SecurityContextHolder.getContext().setAuthentication(securityUser.genAuthentication());
        }
        */

        // accessToken 검증 or refreshToken 발급
        if (!accessToken.isBlank()) {
            // SecurityUser 가져오기
            SecurityUser securityUser = siteUserService.getUserFromAccessToken(accessToken);

            // 인가 처리
            SecurityContextHolder.getContext().setAuthentication(securityUser.getAuthentication());
        }

//        // @AuthenticationPrincipal로 로그인 사용자(userDetails) 꺼내기
//        if (accessToken != null && !accessToken.isBlank() && jwtProvider.verify(accessToken)) {
//            System.out.println("[JwtFilter] Token verified successfully");
//            // 토큰에서 claims 꺼내기
//            String username = (String) jwtProvider.getClaims(accessToken).get("username");
//            System.out.println("[JwtFilter] username from token: " + username);
//
//            // SiteUser 조회
//            SiteUser siteUser = siteUserRepository.findByUserName(username)
//                    .orElseThrow(() -> new RuntimeException("사용자 없음"));
//            System.out.println("[JwtFilter] User found in DB: " + siteUser.getUserName());
//
//            // CustomUserDetails 생성
//            CustomUserDetails userDetails = new CustomUserDetails(siteUser);
//
//            // Spring Security 컨텍스트에 등록
//            UsernamePasswordAuthenticationToken auth =
//                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(auth);
//        }


        filterChain.doFilter(request, response);
    }

    private String _getCookie(String name) {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) return "";

        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(name))
                .findFirst()
                .map(Cookie::getValue)
                .orElse("");
    }

//    private void _addHeaderCookie(String tokenName, String token) {
//        ResponseCookie cookie = ResponseCookie.from(tokenName, token)
//                .path("/")
//                .sameSite("None")
//                .secure(true)
//                .httpOnly(true)
//                .build();
//
//        resp.addHeader("Set-Cookie", cookie.toString());
//    }
}
