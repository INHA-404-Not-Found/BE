package NotFound.next_campus.domain.comment.repository;

import NotFound.next_campus.domain.comment.model.Comment;
import NotFound.next_campus.domain.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);
}
