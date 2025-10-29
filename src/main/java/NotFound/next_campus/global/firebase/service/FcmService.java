package NotFound.next_campus.global.firebase.service;

import NotFound.next_campus.global.auth.user.CustomUserDetails;
import NotFound.next_campus.global.firebase.model.FcmToken;
import NotFound.next_campus.global.firebase.repository.FcmTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FcmService {

    private final FcmTokenRepository fcmTokenRepository;

    public void registerFcmToken(CustomUserDetails userDetails, String token) {

        Optional<FcmToken> existing = fcmTokenRepository.findByToken(token);

        if(existing.isPresent()) {
            // 이미 등록된 토큰인 경우, Member 만 갱신 (재로그인)
            // 특정 기기에서 이미 로그인한 전적이 있는 경우
            FcmToken fcmToken = existing.get();
            fcmToken.setMember(userDetails.getMember());
            return;
        }

        // 새로운 기기에서 로그인
        FcmToken newToken = FcmToken.builder()
                .member(userDetails.getMember())
                .token(token)
                .build();

        fcmTokenRepository.save(newToken);
    }

    public void removeTokensByMember(CustomUserDetails userDetails) {
        fcmTokenRepository.deleteByMember(userDetails.getMember());
    }
}
