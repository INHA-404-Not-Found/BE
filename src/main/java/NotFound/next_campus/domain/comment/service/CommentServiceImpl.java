package NotFound.next_campus.domain.comment.service;

import NotFound.next_campus.domain.comment.dto.request.CreateCommentDTO;
import NotFound.next_campus.domain.comment.dto.request.UpdateCommentDTO;
import NotFound.next_campus.domain.comment.dto.response.CommentResponseDTO;
import NotFound.next_campus.domain.comment.model.Comment;
import NotFound.next_campus.domain.comment.repository.CommentRepository;
import NotFound.next_campus.domain.member.model.Member;
import NotFound.next_campus.domain.member.model.Role;
import NotFound.next_campus.domain.member.repository.MemberRepository;
import NotFound.next_campus.domain.post.model.Post;
import NotFound.next_campus.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    @Override
    public Long saveComment(CreateCommentDTO dto) {

        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

        Comment comment = commentRepository.save(Comment.builder()
                .post(post)
                .member(member)
                .content(dto.getContent())
                .build());

        return comment.getId();
    }

    @Override
    public void updateComment(Long commentId, UpdateCommentDTO dto) {

        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        if (Role.USER.equals(member.getRole())
                && !comment.getMember().equals(member)) {
            throw new IllegalArgumentException("해당 댓글에 대한 수정 권한이 없습니다.");
        }

        comment.setContent(dto.getContent());
    }

    @Override
    public void deleteComment(Long commentId) {

        /* 권한 체크 로직 추가 */

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        commentRepository.delete(comment);
    }

    @Override
    public List<CommentResponseDTO> getCommentsByPost(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

        return commentRepository.findByPost(post).stream()
                .map(CommentResponseDTO::from)
                .toList();
    }
}
