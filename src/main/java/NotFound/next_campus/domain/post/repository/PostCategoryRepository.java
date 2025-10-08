package NotFound.next_campus.domain.post.repository;

import NotFound.next_campus.domain.post.model.Post;
import NotFound.next_campus.domain.post.model.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {

    void deleteByPost(Post post);
}
