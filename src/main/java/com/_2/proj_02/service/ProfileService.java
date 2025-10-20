package com._2.proj_02.service;

import com._2.proj_02.dto.request.ProfileUpdateDTO;
import com._2.proj_02.dto.response.ProfileResponseDTO;
import com._2.proj_02.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public void verifyPassword(String password) {
        Profile profile = profileRepository.findById(1L) // 실제로는 SecurityContext에서 ID 가져오기
                .orElseThrow(() -> new RuntimeException("프로필 없음"));
        if (!profile.getPassword().equals(password)) {
            throw new RuntimeException("비밀번호 불일치");
        }
    }

    public ProfileResponseDTO getProfile() {
        Profile profile = profileRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("프로필 없음"));
        return new ProfileResponseDTO(profile);
    }

    public void updateProfile(ProfileUpdateDTO dto) {
        Profile profile = profileRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("프로필 없음"));
        profile.updateFromDTO(dto);
        profileRepository.save(profile);
    }

    public void sendPhoneVerification(PhoneVerificationDTO dto) {
        // TODO: SMS 발송 로직
    }

    public void sendEmailVerification(EmailVerificationDTO dto) {
        // TODO: 이메일 발송 로직
    }
}