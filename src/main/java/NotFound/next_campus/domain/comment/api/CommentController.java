package NotFound.next_campus.domain.comment.api;

import NotFound.next_campus.domain.comment.dto.request.CreateCommentDTO;
import NotFound.next_campus.domain.comment.dto.request.UpdateCommentDTO;
import NotFound.next_campus.domain.comment.dto.response.CommentResponseDTO;
import NotFound.next_campus.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> registerComment(
            @RequestBody CreateCommentDTO request
    ) {
        Long commentId = commentService.saveComment(request);

        return ResponseEntity.ok().body(
                Map.of(
                        "message", "댓글 등록 성공",
                        "commentId", commentId
                )
        );
    }

    @PatchMapping("/{comment_id}")
    public ResponseEntity<String> updateComment(
            @PathVariable("comment_id") Long commentId,
            @RequestBody UpdateCommentDTO request
    ) {
        commentService.updateComment(commentId, request);

        return ResponseEntity.ok().body(
                "댓글 수정 성공"
        );
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<String> removeComment(
            @PathVariable("comment_id") Long commentId
    ) {
        commentService.deleteComment(commentId);

        return ResponseEntity.ok().body(
                "댓글 삭제 성공"
        );
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<List<CommentResponseDTO>> getComments(
            @PathVariable("post_id") Long postId
    ) {
        return ResponseEntity.ok().body(
                commentService.getCommentsByPost(postId)
        );
    }
}
