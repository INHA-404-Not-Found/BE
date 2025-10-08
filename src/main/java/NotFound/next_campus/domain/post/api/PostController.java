package NotFound.next_campus.domain.post.api;

import NotFound.next_campus.domain.post.dto.request.CreatePostDTO;
import NotFound.next_campus.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
