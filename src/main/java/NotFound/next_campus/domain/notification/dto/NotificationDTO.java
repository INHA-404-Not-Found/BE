package NotFound.next_campus.domain.notification.dto;

import NotFound.next_campus.domain.notification.model.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class NotificationDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRequest {
        private Long memberId;
        private String title;
        private String message;
        private String link;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long notificationId;
        private String title;
        private String message;
        private String link;
        private Boolean isRead;

        public static Response toResponse(Notification notification) {

            return Response.builder()
                    .notificationId(notification.getId())
                    .title(notification.getTitle())
                    .message(notification.getMessage())
                    .link(notification.getLink())
                    .isRead(notification.getIsRead())
                    .build();
        }
    }
}
