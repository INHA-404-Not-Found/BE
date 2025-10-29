package NotFound.next_campus.domain.notification.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotificationDTO {
    private Long memberId;
    private String title;
    private String message;
    private String link;
}
