//package com._2.proj_02.global.config;
//
//import com._2.proj_02.domain.auth.entity.SiteUser;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.Collections;
//
//public class CustomUserDetails implements UserDetails {
//
//    private final SiteUser siteUser;
//
//    public CustomUserDetails(SiteUser siteUser) {
//        this.siteUser = siteUser;
//    }
//
//    public SiteUser getUser() {
//        return siteUser;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // 권한 설정 (필요 없으면 비워둬도 OK)
//        return Collections.emptyList();
//    }
//
//    @Override
//    public String getPassword() {
//        return siteUser.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return siteUser.getUserName();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
