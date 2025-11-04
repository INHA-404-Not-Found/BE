package NotFound.next_campus.domain.notification.service;

import NotFound.next_campus.domain.notification.dto.NotificationDTO;
import NotFound.next_campus.global.auth.user.CustomUserDetails;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {

    /* 한 명의 사용자에게 알림 보내기 */
    void sendAndSaveNotification(NotificationDTO.CreateRequest dto);

    /* 특정 사용자가 받은 알림 목록 조회 */
    List<NotificationDTO.Response> getNotifications(CustomUserDetails userDetails, Pageable pageable, int pageNo);

    /* 알림 읽기 */
    void markAsRead(Long id, CustomUserDetails userDetails);

    /* 안읽은 알림 개수 조회 하는 함수 있으면 좋을듯 */
}
