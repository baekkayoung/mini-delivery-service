package com.example.delivery.user.service;

import com.example.delivery.user.dto.request.SignupRequestDto;
import com.example.delivery.user.dto.response.SignupResponseDto;
import com.example.delivery.user.entity.User;
import com.example.delivery.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupResponseDto signUp(SignupRequestDto request) {

        if(userRepository.existsByUsername(request.getUsername())){
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(request.getUsername(), encodedPassword, request.getRole());

        User signupedUser = userRepository.save(user);

        return new SignupResponseDto(signupedUser);
    }

}
