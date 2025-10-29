package NotFound.next_campus.domain.notification.service;

import NotFound.next_campus.domain.member.model.Member;
import NotFound.next_campus.domain.member.repository.MemberRepository;
import NotFound.next_campus.domain.notification.dto.request.CreateNotificationDTO;
import NotFound.next_campus.domain.notification.dto.response.NotificationResponseDTO;
import NotFound.next_campus.domain.notification.model.Notification;
import NotFound.next_campus.domain.notification.repository.NotificationRepository;
import NotFound.next_campus.global.auth.user.CustomUserDetails;
import NotFound.next_campus.global.firebase.repository.FcmTokenRepository;
import NotFound.next_campus.global.firebase.service.FirebaseMessagingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl {

    private final MemberRepository memberRepository;
    private final NotificationRepository notificationRepository;

    private final FcmTokenRepository fcmTokenRepository;
    private final FirebaseMessagingService firebaseMessagingService;

    private static int PAGE_LIMIT = 10;

    // 특정 유저에게 알림 보냄
    public void sendAndSaveNotification(CreateNotificationDTO dto) {

        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 해당 회원의 모든 FCM 토큰 조회
        List<String> tokens = fcmTokenRepository.findTokensByMember(member);

        // FCM 토큰이 없는 경우 그냥 넘어감
        if (tokens.isEmpty()) {

            log.warn("[알림 전송 실패] memberId={} 의 FCM 토큰이 존재하지 않습니다.", member.getId());
            return;
        }

        for (String token : tokens) {
            firebaseMessagingService.sendNotification(
                    token,
                    dto.getTitle(),
                    dto.getMessage()
            );
        }

        notificationRepository.save(
                Notification.builder()
                        .member(member)
                        .title(dto.getTitle())
                        .message(dto.getMessage())
                        .link(dto.getLink())
                        .isRead(false)
                        .build()
        );

        log.info("[알림 저장 및 전송 성공] memberId={} ({})", member.getId(), dto.getTitle());
    }

    public List<NotificationResponseDTO> getNotifications(CustomUserDetails userDetails, Pageable pageable, int pageNo) {

        pageable = PageRequest.of(pageNo, PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Notification> notificationPage = notificationRepository.findByMember(userDetails.getMember(), pageable);

        List<Notification> notifications = notificationPage.getContent();

        return notifications.stream()
                .map(NotificationResponseDTO::toResponse)
                .toList();
    }

    public void markAsRead(Long id, CustomUserDetails userDetails) {

        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 알림입니다."));

        if (!notification.getMember().equals(userDetails.getMember())) {
            throw new AccessDeniedException("해당 알림에 대한 읽기 권한이 없습니다.");
        }

        notification.setIsRead(true);
    }

    /* 안읽은 알림 개수 조회 하는 함수 있으면 좋을듯 */
}
