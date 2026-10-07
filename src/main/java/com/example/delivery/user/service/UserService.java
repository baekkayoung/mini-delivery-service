package com.example.delivery.user.service;

import com.example.delivery.jwt.JwtUtil;
import com.example.delivery.user.dto.request.LoginRequestDto;
import com.example.delivery.user.dto.request.SignupRequestDto;
import com.example.delivery.user.dto.response.SignupResponseDto;
import com.example.delivery.user.entity.User;
import com.example.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public SignupResponseDto signUp(SignupRequestDto request) {

        if(userRepository.existsByUsername(request.getUsername())){
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(request.getUsername(), encodedPassword, request.getRole());

        User signupedUser = userRepository.save(user);

        return new SignupResponseDto(signupedUser);
    }

    public String login(LoginRequestDto request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "존재하지 않는 아이디 입니다."
                ));

        boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if(!matches){
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "비밀번호가 일치하지 않습니다."
            );
        }

        return jwtUtil.createToken(user.getUsername(), user.getRole());

    }
}
