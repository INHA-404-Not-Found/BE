package NotFound.next_campus.global.auth.token.dto.request;

public class LoginRequest {
    private Long studentId;
    private String password;
    private Boolean isWeb; // true -> web login (cookie), false -> app (json)


    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Boolean getIsWeb() { return isWeb == null ? false : isWeb; }
    public void setIsWeb(Boolean isWeb) { this.isWeb = isWeb; }
}