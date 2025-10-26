package NotFound.next_campus.global.auth.token.dto.request;

// 앱에서 refresh token을 바디로 보낼 때 사용
public class RefreshRequest {
    private String refreshToken;
    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}