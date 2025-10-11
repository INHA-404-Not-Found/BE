package NotFound.next_campus.global.auth.token.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


/**
 * JWT 토큰 생성/검증 유틸
 * - secret은 application.properties 또는 환경변수로 지정
 * - access/refresh 만료시간(ms)도 프로퍼티로 주입
 */
@Component
public class JwtTokenProvider {
    private final SecretKey key;
    public final long accessTokenMillis;
    public final long refreshTokenMillis;


    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expiration-ms:900000}") long accessTokenMillis,
            @Value("${jwt.refresh-token-expiration-ms:1209600000}") long refreshTokenMillis
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenMillis = accessTokenMillis;
        this.refreshTokenMillis = refreshTokenMillis;
    }


    // Access Token 생성 (subject에 email 포함)
    public String createAccessToken(String email) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenMillis))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }


    // Refresh Token 생성
    public String createRefreshToken(String email) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenMillis))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }


    // 토큰에서 subject(email) 추출
    public String getSubjectFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }


    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }
}