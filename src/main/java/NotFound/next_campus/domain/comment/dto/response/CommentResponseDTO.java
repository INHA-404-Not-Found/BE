package NotFound.next_campus.domain.comment.dto.response;

import NotFound.next_campus.domain.comment.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDTO {
    private Long commentId;
    private String writer;
    private String content;

    public static CommentResponseDTO from(Comment comment) {

        return CommentResponseDTO.builder()
                .commentId(comment.getId())
                .writer(comment.getMember().getName())
                .content(comment.getContent())
                .build();
    }
}
