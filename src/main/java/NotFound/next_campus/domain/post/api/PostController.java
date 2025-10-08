package NotFound.next_campus.domain.post.api;

import NotFound.next_campus.domain.post.dto.request.CreatePostCategoryDTO;
import NotFound.next_campus.domain.post.dto.request.CreatePostDTO;
import NotFound.next_campus.domain.post.service.PostCategoryService;
import NotFound.next_campus.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final PostCategoryService postCategoryService;

    @PostMapping
    public ResponseEntity<String> registerPost(
            @RequestBody CreatePostDTO request
    ) {
        postService.savePost(request);

        return ResponseEntity.ok().body(
                "게시물 등록 성공"
        );
    }

    @PostMapping("/{post_id}/images")
    public ResponseEntity<String> registerPostImage(
            @PathVariable("post_id") Long postId,
            @RequestParam("file") MultipartFile file
    ) {
        postService.savePostImage(postId, file);

        return ResponseEntity.ok().body(
                "이미지 등록 성공"
        );
    }

    @PostMapping("/{post_id}/categories")
    public ResponseEntity<String> registerPostCategory(
            @PathVariable("post_id") Long postId,
            @RequestBody CreatePostCategoryDTO request
    ) {
        postCategoryService.savePostCategories(postId, request.getCategories());

        return ResponseEntity.ok().body(
                "게시할 분실물 카테고리 등록 성공"
        );
    }
}
