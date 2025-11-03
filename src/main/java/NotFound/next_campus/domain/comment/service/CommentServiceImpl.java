package NotFound.next_campus.domain.comment.service;

import NotFound.next_campus.domain.comment.dto.CommentDTO;
import NotFound.next_campus.domain.comment.model.Comment;
import NotFound.next_campus.domain.comment.repository.CommentRepository;
import NotFound.next_campus.domain.member.model.Member;
import NotFound.next_campus.domain.member.model.Role;
import NotFound.next_campus.domain.member.repository.MemberRepository;
import NotFound.next_campus.domain.post.model.Post;
import NotFound.next_campus.domain.post.repository.PostRepository;
import NotFound.next_campus.global.auth.user.CustomUserDetails;
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
    public Long saveComment(CommentDTO.CreateRequest dto, CustomUserDetails userDetails) {

        Member member = userDetails.getMember();
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
    public void updateComment(Long commentId, CommentDTO.UpdateRequest dto, CustomUserDetails userDetails) {

        Member member = userDetails.getMember();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        // 본인이 등록한 댓글이거나 관리자인 경우에만 수정 가능
        if (!comment.getMember().equals(member) &&
                !Role.ADMIN.equals(member.getRole())) {
            throw new IllegalArgumentException("해당 댓글에 대한 수정 권한이 없습니다.");
        }

        comment.setContent(dto.getContent());
    }

    @Override
    public void deleteComment(Long commentId, CustomUserDetails userDetails) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        // 본인이 등록한 댓글이거나 관리자인 경우에만 삭제 가능
        if (!comment.getMember().equals(userDetails.getMember()) &&
                !Role.ADMIN.equals(userDetails.getRole())) {
            throw new IllegalArgumentException("해당 댓글에 대한 삭제 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }

    @Override
    public List<CommentDTO.Response> getCommentsByPost(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

        return commentRepository.findByPost(post).stream()
                .map(CommentDTO.Response::from)
                .toList();
    }
}
