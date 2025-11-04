package NotFound.next_campus.domain.post.repository;

import NotFound.next_campus.domain.member.model.Member;
import NotFound.next_campus.domain.post.model.Post;
import NotFound.next_campus.domain.post.model.PostStatus;
import NotFound.next_campus.domain.post.model.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAll(Pageable pageable);

    Page<Post> findByMember(Member member,
                            Pageable pageable);

    @Query("SELECT DISTINCT p FROM Post p " +
            "WHERE (:status IS NULL or p.status = :status) " +
            "AND (:type IS NULL or p.type = :type) " +
            "AND (:locationId IS NULL or p.location.id = :locationId) " +
            "AND (:categoryId IS NULL or EXISTS ( " +
            "            SELECT 1 FROM PostCategory pc " +
            "            WHERE pc.post = p AND pc.category.id = :categoryId" +
            "            ))")
    Page<Post> findPostsByTags(@Param("status") PostStatus status,
                               @Param("type") PostType type,
                               @Param("locationId") Long locationId,
                               @Param("categoryId") Long categoryId,
                               Pageable pageable);

    @Query("SELECT p FROM Post p " +
            "WHERE p.title LIKE CONCAT('%', :keyword, '%') " +
            "OR p.content LIKE CONCAT('%', :keyword, '%')")
    Page<Post> findAllSearch(@Param("keyword") String keyword,
                             Pageable pageable);

    /* 특정 카테고리 목록에 속하는 분실 신고 게시물 작성자 목록 조회 */
    @Query("SELECT DISTINCT pc.post.member " +
            "FROM PostCategory pc " +
            "WHERE pc.category.id IN :categoryIds " +
            "AND pc.post.type = :type " +
            "AND pc.post.status = :status " +
            "AND pc.post.member <> :member")
    List<Member> findDistinctMembersByCategoryIdsAndType(@Param("categoryIds") List<Long> categoryIds,
                                                         @Param("type") PostType type,
                                                         @Param("status") PostStatus status,
                                                         @Param("member") Member member);
}
