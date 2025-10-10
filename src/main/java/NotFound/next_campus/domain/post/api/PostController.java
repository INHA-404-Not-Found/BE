package NotFound.next_campus.domain.post.api;

import NotFound.next_campus.domain.post.dto.request.CreatePostDTO;
import NotFound.next_campus.domain.post.dto.request.DeletePostDTO;
import NotFound.next_campus.domain.post.dto.request.UpdatePostDTO;
import NotFound.next_campus.domain.post.dto.request.UpdatePostStatusDTO;
import NotFound.next_campus.domain.post.dto.response.PostResponseDTO;
import NotFound.next_campus.domain.post.model.PostStatus;
import NotFound.next_campus.domain.post.model.PostType;
import NotFound.next_campus.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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

    @PatchMapping("/update")
    public ResponseEntity<String> modifyPosts(
            @RequestBody UpdatePostStatusDTO request
    ) {
        postService.updateStatusOfPosts(request);

        return ResponseEntity.ok().body(
                "게시물 인계 여부 일괄 수정 성공"
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

    @PostMapping("/delete")
    public ResponseEntity<String> removePosts(
            @RequestBody DeletePostDTO request
    ) {
        postService.deletePosts(request.getPostIds(), request.getMemberId());

        return ResponseEntity.ok().body(
                "게시물 일괄 삭제 성공"
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

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {

        return ResponseEntity.ok().body(
                postService.getAllPostList()
        );
    }

    @GetMapping("tags")
    public ResponseEntity<List<PostResponseDTO>> getPostsByTags(
            @RequestParam(value = "status", required = false) PostStatus status,
            @RequestParam(value = "type", required = false) PostType type,
            @RequestParam(value = "location_id", required = false) Long locationId,
            @RequestParam(value = "category_id", required = false) Long categoryId
    ) {
        return ResponseEntity.ok().body(
                postService.getPostsByTags(status, type, locationId, categoryId)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostResponseDTO>> getPostsByKeyword(
            @RequestParam("keyword") String keyword
    ) {
        return ResponseEntity.ok().body(
                postService.getPostsByKeyword(keyword)
        );
    }

    @GetMapping("paging")
    public ResponseEntity<Page<PostResponseDTO>> getPostsByPaging(Pageable pageable) {
        return ResponseEntity.ok().body(
                postService.getPostsByPaging(pageable)
        );
    }
}
