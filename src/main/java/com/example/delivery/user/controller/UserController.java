package com.example.delivery.user.controller;

import com.example.delivery.jwt.JwtUtil;
import com.example.delivery.user.dto.request.LoginRequestDto;
import com.example.delivery.user.dto.request.SignupRequestDto;
import com.example.delivery.user.dto.response.SignupResponseDto;
import com.example.delivery.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<SignupResponseDto> signUp(
            @Valid @RequestBody SignupRequestDto request
    ) {
        SignupResponseDto response = userService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/users/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequestDto request) {
        String token = userService.login(request);
        return ResponseEntity.ok().header(JwtUtil.AUTHORIZATION_HEADER, token).build();
    }


}
