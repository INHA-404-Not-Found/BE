package NotFound.next_campus.domain.post.api;

import NotFound.next_campus.domain.post.dto.request.CreatePostDTO;
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
}
