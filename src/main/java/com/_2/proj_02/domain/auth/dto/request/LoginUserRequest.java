package com._2.proj_02.domain.auth.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginUserRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
