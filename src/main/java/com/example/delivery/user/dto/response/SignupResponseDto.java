package com.example.delivery.user.dto.response;

import com.example.delivery.user.entity.User;
import com.example.delivery.user.entity.UserRole;
import lombok.Getter;

@Getter
public class SignupResponseDto {
    private Long id;
    private String username;
    private UserRole role;

    public SignupResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();
    }
}
