package NotFound.next_campus.global.auth.token.dto.request;

public class LoginRequest {
    private String email;
    private String password;
    private Boolean isWeb; // true -> web login (cookie), false -> app (json)


    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Boolean getIsWeb() { return isWeb == null ? false : isWeb; }
    public void setIsWeb(Boolean isWeb) { this.isWeb = isWeb; }
}