package NotFound.next_campus.domain.comment.service;

import NotFound.next_campus.domain.comment.dto.CommentDTO;
import NotFound.next_campus.global.auth.user.CustomUserDetails;

import java.util.List;

public interface CommentService {

    /* 댓글 등록 */
    Long saveComment(CommentDTO.CreateRequest dto, CustomUserDetails userDetails);

    /* 댓글 수정 */
    void updateComment(Long commentId, CommentDTO.UpdateRequest dto, CustomUserDetails userDetails);

    /* 댓글 삭제 */
    void deleteComment(Long commentId, CustomUserDetails userDetails);

    /* 댓글 목록 조회 */
    List<CommentDTO.Response> getCommentsByPost(Long postId);
}
