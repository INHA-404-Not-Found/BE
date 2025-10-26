package NotFound.next_campus.global.auth.token.dto.response;

public class LoginResponse {
    private String accessToken; // 접근 토큰 (API 요청 시 Authorization 헤더에 넣음)
    private String refreshToken; // 웹일 때는 null, 앱용으로만 반환 (웹은 HttpOnly 쿠키로 제공)

    public LoginResponse() {}
    public LoginResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }


    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}