package NotFound.next_campus.global.firebase.api;

import NotFound.next_campus.global.auth.user.CustomUserDetails;
import NotFound.next_campus.global.firebase.dto.request.FcmTokenRequest;
import NotFound.next_campus.global.firebase.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fcm")
public class FcmController {

    private final FcmService fcmService;

    @PostMapping("/token")
    public ResponseEntity<String> registerToken(
            @RequestBody FcmTokenRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        String token = request.getToken();

        if(token == null || token.isBlank()) {

            return ResponseEntity.badRequest().body(
                    "FCM token 필요"
            );
        }

        fcmService.registerFcmToken(userDetails, token);

        return ResponseEntity.ok().body(
                "FCM token 생성 성공"
        );
    }

    @DeleteMapping("/token")
    public ResponseEntity<String> deleteToken(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        fcmService.removeTokensByMember(userDetails);
        
        return ResponseEntity.ok().body(
                "FCM tokens 삭제 성공"
        );
    }
}
