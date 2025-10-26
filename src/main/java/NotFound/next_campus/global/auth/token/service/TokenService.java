package NotFound.next_campus.global.auth.token.service;

import NotFound.next_campus.global.auth.token.dto.request.LoginRequest;
import NotFound.next_campus.global.auth.token.exception.TokenException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import jakarta.transaction.Transactional;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
public class TokenService {
    private final AuthenticationManager authenticationManager;
    public final JwtTokenProvider tokenProvider; //일단 public으로 바꿈
    private final MemberService memberService;


    public TokenService(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, MemberService memberService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.memberService = memberService;
    }

    @Transactional
    public LoginTokens login(LoginRequest req) {
        // 1) 인증 수행 (UserDetailsService와 PasswordEncoder로 검증)
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getStudentId(), req.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);


        // 2) 토큰 생성
        String access = tokenProvider.createAccessToken(req.getStudentId());
        String refresh = tokenProvider.createRefreshToken(req.getStudentId());


        // 3) DB에 refresh 저장 (expiry는 ISO_INSTANT 포맷)
        Instant expiry = Instant.now().plusMillis(tokenProvider.refreshTokenMillis);
        String expiryIso = DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC).format(expiry);
        memberService.saveRefreshToken(req.getStudentId(), refresh, expiryIso);

        return new LoginTokens(access, refresh);
    }

    @Transactional
    public LoginTokens refresh(String refreshToken) {
        // 토큰 자체 유효성 검증
        if (refreshToken == null || !tokenProvider.validateToken(refreshToken)) throw new TokenException("Invalid refresh token");
        String studentId = tokenProvider.getSubjectFromToken(refreshToken);


        // DB에 저장된 토큰과 비교
        String stored = memberService.getRefreshToken(Long.valueOf(studentId));
        if (stored == null || !stored.equals(refreshToken)) throw new TokenException("Refresh token not found or mismatched");


        // 만료 검사 (DB에 저장된 expiry와 비교)
        String expiryIso = memberService.getRefreshExpiry(Long.valueOf(studentId));
        Instant expiry = memberService.parseExpiry(expiryIso);
        if (expiry == null || Instant.now().isAfter(expiry)) {
            // 만료된 경우 DB에서 삭제 후 인증 실패
            memberService.clearRefreshToken(Long.valueOf(studentId));
            throw new TokenException("Refresh token expired");
        }


        // 새 토큰 발급 및 DB 갱신
        String newAccess = tokenProvider.createAccessToken(Long.valueOf(studentId));
        String newRefresh = tokenProvider.createRefreshToken(Long.valueOf(studentId));
        Instant newExpiry = Instant.now().plusMillis(tokenProvider.refreshTokenMillis);
        String newExpiryIso = DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC).format(newExpiry);
        memberService.saveRefreshToken(Long.valueOf(studentId), newRefresh, newExpiryIso);


        return new LoginTokens(newAccess, newRefresh);
    }


    @Transactional
    public void logout(String refreshToken) {
        if (refreshToken == null) return;
        if (!tokenProvider.validateToken(refreshToken)) return;
        String studentId = tokenProvider.getSubjectFromToken(refreshToken);
        memberService.clearRefreshToken(Long.valueOf(studentId));
    }


    // 내부 토큰 보관용 DTO
    public static class LoginTokens {
        public final String accessToken;
        public final String refreshToken;
        public LoginTokens(String a, String r) { this.accessToken = a; this.refreshToken = r; }
    }
}