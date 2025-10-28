package NotFound.next_campus.domain.receiver.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateReceiverDTO {
    // private Long memberId;
    private Long postId;
    private String name;
    private String email;
    private String phoneNumber;
    private String studentId;
}
