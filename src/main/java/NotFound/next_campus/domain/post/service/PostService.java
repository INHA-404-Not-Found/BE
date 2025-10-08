package NotFound.next_campus.domain.post.service;

import NotFound.next_campus.domain.post.dto.request.CreatePostDTO;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    /* 게시물 등록 */
    Long savePost(CreatePostDTO dto);
    void savePostImage(Long postId, MultipartFile file);
}
