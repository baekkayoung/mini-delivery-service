package com.example.delivery.jwt;


import com.example.delivery.user.entity.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    // JWT 토큰 접두사
    public static final String BEARER_PREFIX = "Bearer ";

    // JWT Secret Key
    @Value("${jwt.secret.key}")
    private String secretKey;

    // HTTP Authorization 헤더 이름
    public static final String AUTHORIZATION_HEADER = "Authorization";

    // JWT Claims에서 사용자 권한을 저장할 때 사용하는 key
    public static final String AUTHORIZATION_KEY = "auth";

    // 토큰 만료 시간 - 1시간
    private final long TOKEN_TIME = 60 * 60 * 1000L;


    // JWT 서명에 사용할 Key
    private SecretKey key;
    // JWT 서명 알고리즘
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        // 문자열 -> byte 변환
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        // JWT 서명용 Key 객체로
        key = Keys.hmacShaKeyFor(bytes);
    }

    // Jwt 토큰 생성
    public String createToken(String username, UserRole role){

        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .claim(AUTHORIZATION_KEY, role)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    // Http Header의 Authorization 헤더에 있는 JWT 가져오기
    public String getTokenFromRequest(HttpServletRequest req) {
        String bearerToken = req.getHeader(AUTHORIZATION_HEADER);
        return bearerToken;
    }

    // Bearer 제거
    public String substringToken(String bearerToken) {

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        throw new NullPointerException("Not Found Token");
    }

    // JWT 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (SecurityException | MalformedJwtException e) {
            return false;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    // JWT 토큰에서 username, role 등 꺼내기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
