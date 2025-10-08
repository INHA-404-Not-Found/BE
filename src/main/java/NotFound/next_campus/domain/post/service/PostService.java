package NotFound.next_campus.domain.post.service;

import NotFound.next_campus.domain.post.dto.request.CreatePostDTO;
import NotFound.next_campus.domain.post.dto.request.UpdatePostDTO;
import NotFound.next_campus.domain.post.dto.response.PostResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    /* 게시물 등록 */
    Long savePost(CreatePostDTO dto);
    void savePostImage(Long postId, MultipartFile file);

    /* 게시물 수정 */
    Long updatePost(Long postId, UpdatePostDTO dto);
    void updatePostImage(Long postId, MultipartFile file);

    /* 게시물 삭제 */
    void deletePost(Long postId, Long memberId);

    /* 게시물 조회 */
    PostResponseDTO getPostById(Long postId);
}
