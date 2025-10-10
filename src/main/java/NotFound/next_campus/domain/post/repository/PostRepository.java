package NotFound.next_campus.domain.post.repository;

import NotFound.next_campus.domain.post.model.Post;
import NotFound.next_campus.domain.post.model.PostStatus;
import NotFound.next_campus.domain.post.model.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT DISTINCT p FROM Post p " +
            "WHERE (:status IS NULL or p.status = :status) " +
            "AND (:type IS NULL or p.type = :type) " +
            "AND (:locationId IS NULL or p.location.id = :locationId) " +
            "AND (:categoryId IS NULL or EXISTS ( " +
            "            SELECT 1 FROM PostCategory pc " +
            "            WHERE pc.post = p AND pc.category.id = :categoryId" +
            "            ))")
    List<Post> findPostsByTags(@Param("status") PostStatus status,
                               @Param("type") PostType type,
                               @Param("locationId") Long locationId,
                               @Param("categoryId") Long categoryId);

    @Query("SELECT p FROM Post p " +
            "WHERE p.title LIKE CONCAT('%', :keyword, '%') " +
            "OR p.content LIKE CONCAT('%', :keyword, '%')")
    List<Post> findAllSearch(@Param("keyword") String keyword);
}
