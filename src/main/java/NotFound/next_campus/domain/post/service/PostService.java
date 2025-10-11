package NotFound.next_campus.domain.post.service;

import NotFound.next_campus.domain.post.dto.request.CreatePostDTO;
import NotFound.next_campus.domain.post.dto.request.DeletePostDTO;
import NotFound.next_campus.domain.post.dto.request.UpdatePostDTO;
import NotFound.next_campus.domain.post.dto.request.UpdatePostStatusDTO;
import NotFound.next_campus.domain.post.dto.response.PostResponseDTO;
import NotFound.next_campus.domain.post.model.PostStatus;
import NotFound.next_campus.domain.post.model.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    /* 게시물 등록 */
    Long savePost(CreatePostDTO dto);
    void savePostImages(Long postId, List<MultipartFile> files);

    /* 게시물 수정 */
    Long updatePost(Long postId, UpdatePostDTO dto);
    void updatePostImages(Long postId, List<MultipartFile> files);
    /* 게시물 인계 여부 일괄 수정 */
    void updateStatusOfPosts(UpdatePostStatusDTO dto);

    /* 게시물 삭제 */
    void deletePost(Long postId, Long memberId);
    /* 게시물 일괄 삭제 */
    void deletePosts(List<Long> postIds, Long memberId);

    /* 특정 게시물 내용 조회 */
    PostResponseDTO getPostById(Long postId);

    /* 전체 게시물 목록 조회 */
    List<PostResponseDTO> getAllPostList(Pageable pageable, int pageNo);

    /* 게시물 목록 필터링 조회 */
    List<PostResponseDTO> getPostsByTags(PostStatus status, PostType type, Long locationId, Long categoryId,
                                         Pageable pageable, int pageNo);

    /* 게시물 키워드 검색 */
    List<PostResponseDTO> getPostsByKeyword(String keyword, Pageable pageable, int pageNo);
}
