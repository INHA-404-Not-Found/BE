package NotFound.next_campus.domain.notification.dto.response;

import NotFound.next_campus.domain.notification.model.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDTO {
    private String title;
    private String message;
    private String link;
    private Boolean isRead;

    public static NotificationResponseDTO toResponse(Notification notification) {

        return NotificationResponseDTO.builder()
                .title(notification.getTitle())
                .message(notification.getMessage())
                .link(notification.getLink())
                .isRead(notification.getIsRead())
                .build();
    }
}
