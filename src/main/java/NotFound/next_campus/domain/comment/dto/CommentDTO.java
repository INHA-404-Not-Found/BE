package NotFound.next_campus.domain.comment.dto;

import NotFound.next_campus.domain.comment.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRequest {
        private Long postId;
        // private Long memberId;
        private String content;
    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRequest {
        // private Long memberId;
        private String content;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long commentId;
        private String writer;
        private String content;

        public static Response from(Comment comment) {

            return Response.builder()
                    .commentId(comment.getId())
                    .writer(comment.getMember().getName())
                    .content(comment.getContent())
                    .build();
        }
    }
}
