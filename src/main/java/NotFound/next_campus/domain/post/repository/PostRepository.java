package NotFound.next_campus.domain.post.repository;

import NotFound.next_campus.domain.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
