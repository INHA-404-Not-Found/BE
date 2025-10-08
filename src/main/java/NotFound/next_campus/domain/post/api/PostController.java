package NotFound.next_campus.domain.post.api;

import NotFound.next_campus.domain.post.dto.request.CreatePostDTO;
import NotFound.next_campus.domain.post.dto.request.UpdatePostDTO;
import NotFound.next_campus.domain.post.dto.response.PostResponseDTO;
import NotFound.next_campus.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> registerPost(
            @RequestBody CreatePostDTO request
    ) {
        Long postId = postService.savePost(request);

        return ResponseEntity.ok().body(
                Map.of(
                        "message", "게시물 등록 성공",
                        "postId", postId
                )
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

    @PatchMapping("/{post_id}")
    public ResponseEntity<String> modifyPost(
            @PathVariable("post_id") Long postId,
            @RequestBody UpdatePostDTO request
    ) {
        postService.updatePost(postId, request);

        return ResponseEntity.ok().body(
                "게시물 수정 성공"
        );
    }

    @PatchMapping("/{post_id}/images")
    public ResponseEntity<String> modifyPostImage(
            @PathVariable("post_id") Long postId,
            @RequestParam("file") MultipartFile file
    ) {
        postService.updatePostImage(postId, file);

        return ResponseEntity.ok().body(
                "게시물 이미지 수정 성공"
        );
    }

    @DeleteMapping("/{post_id}")
    public ResponseEntity<String> removePost(
            @PathVariable("post_id") Long postId
    ) {
        postService.deletePost(postId, 1L);

        return ResponseEntity.ok().body(
                "게시물 삭제 성공"
        );
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<PostResponseDTO> getPost(
            @PathVariable("post_id") Long postId
    ) {
        return ResponseEntity.ok().body(
                postService.getPostById(postId)
        );
    }
}
