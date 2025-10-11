package NotFound.next_campus.global.auth.token.service;
// 토큰 자체 유효성 검증
if (refreshToken == null || !tokenProvider.validateToken(refreshToken)) throw new TokenException("Invalid refresh token");
String email = tokenProvider.getSubjectFromToken(refreshToken);


// DB에 저장된 토큰과 비교
String stored = memberService.getRefreshToken(email);
if (stored == null || !stored.equals(refreshToken)) throw new TokenException("Refresh token not found or mismatched");


// 만료 검사 (DB에 저장된 expiry와 비교)
String expiryIso = memberService.getRefreshExpiry(email);
Instant expiry = memberService.parseExpiry(expiryIso);
if (expiry == null || Instant.now().isAfter(expiry)) {
// 만료된 경우 DB에서 삭제 후 인증 실패
        memberService.clearRefreshToken(email);
throw new TokenException("Refresh token expired");
}


// 새 토큰 발급 및 DB 갱신
String newAccess = tokenProvider.createAccessToken(email);
String newRefresh = tokenProvider.createRefreshToken(email);
Instant newExpiry = Instant.now().plusMillis(tokenProvider.refreshTokenMillis);
String newExpiryIso = DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC).format(newExpiry);
memberService.saveRefreshToken(email, newRefresh, newExpiryIso);


return new LoginTokens(newAccess, newRefresh);
}


@Transactional
public void logout(String refreshToken) {
    if (refreshToken == null) return;
    if (!tokenProvider.validateToken(refreshToken)) return;
    String email = tokenProvider.getSubjectFromToken(refreshToken);
    memberService.clearRefreshToken(email);
}


// 내부 토큰 보관용 DTO
public static class LoginTokens {
    public final String accessToken;
    public final String refreshToken;
    public LoginTokens(String a, String r) { this.accessToken = a; this.refreshToken = r; }
}
}