package NotFound.next_campus.domain.receiver.dto.response;

import NotFound.next_campus.domain.receiver.model.Receiver;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiverResponseDTO {
    private Long receiverId;
    private String name;
    private String email;
    private String phoneNumber;
    private String studentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ReceiverResponseDTO from(Receiver receiver) {

        return ReceiverResponseDTO.builder()
                .receiverId(receiver.getId())
                .name(receiver.getName())
                .email(receiver.getEmail())
                .phoneNumber(receiver.getPhoneNumber())
                .studentId(receiver.getStudentId())
                .createdAt(receiver.getCreatedAt())
                .updatedAt(receiver.getUpdatedAt())
                .build();
    }
}
