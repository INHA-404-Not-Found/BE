package NotFound.next_campus.domain.post.repository;

import NotFound.next_campus.domain.post.model.Post;
import NotFound.next_campus.domain.post.model.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    List<PostImage> findByPost(Post post);

    @Query("SELECT pi FROM PostImage pi " +
            "JOIN FETCH pi.post " +
            "WHERE pi.post IN :posts")
    List<PostImage> findAllByPosts(@Param("posts") List<Post> posts);
}
