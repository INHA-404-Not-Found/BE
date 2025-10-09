package NotFound.next_campus.domain.post.repository;

import NotFound.next_campus.domain.category.model.Category;
import NotFound.next_campus.domain.post.model.Post;
import NotFound.next_campus.domain.post.model.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {

    void deleteByPost(Post post);

    List<PostCategory> findByPost(Post post);
}
