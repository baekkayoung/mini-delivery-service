package com.example.delivery.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequestDto {
    @NotBlank(message = "4글자 이상 입력하세요.")
    private String username;
    @NotBlank(message = "8글자 이상 입력하세요")
    private String password;

}
