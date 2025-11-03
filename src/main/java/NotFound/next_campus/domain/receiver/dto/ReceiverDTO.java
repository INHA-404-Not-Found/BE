package NotFound.next_campus.domain.receiver.dto;

import NotFound.next_campus.domain.receiver.model.Receiver;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ReceiverDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRequest {
        // private Long memberId;
        private Long postId;
        private String name;
        private String email;
        private String phoneNumber;
        private String studentId;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRequest {
        // private Long memberId;
        private String name;
        private String email;
        private String phoneNumber;
        private String studentId;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long receiverId;
        private String name;
        private String email;
        private String phoneNumber;
        private String studentId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static Response from(Receiver receiver) {

            return Response.builder()
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
}
