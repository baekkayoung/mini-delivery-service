package com.example.delivery.jwt;

import com.example.delivery.security.UserDetailsServiceImpl;
import com.example.delivery.user.entity.UserRole;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Authorization 헤더에서 JWT 가져오기
        String bearerToken = jwtUtil.getTokenFromRequest(request);

        System.out.println("Authorization Header = " + bearerToken);

        // 토큰이 없으면 다음 필터로 넘김
        if (bearerToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Bearer 제거
            String token = jwtUtil.substringToken(bearerToken);

            // JWT 검증
            if (!jwtUtil.validateToken(token)) {
                System.out.println("===== JWT 검증 실패 =====");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("""
            {
                "status": 401,
                "message": "유효하지 않은 토큰입니다."
            }
            """);
                return;
            }

            // JWT에서 사용자 정보 가져오기
            Claims claims = jwtUtil.getUserInfoFromToken(token);

            // User 아이디
            String username = claims.getSubject();

            // Token에서 가져온 username으로 DB에서 사용자를 조회하여 UserDetails로 변환
            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            // Authentication 객체 생성
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            // SecurityContext에 인증 정보 저장
            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

        } catch (Exception e) {
            // 토큰이 잘못된 경우 인증 정보를 넣지 않음
//            SecurityContextHolder.clearContext();\
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("""
            {
                "status": 401,
                "message": "유효하지 않은 토큰입니다."
            }
            """);
            return;
        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }


}
