package NotFound.next_campus.domain.post.api;

import NotFound.next_campus.domain.post.dto.PostDTO;
import NotFound.next_campus.domain.post.model.PostStatus;
import NotFound.next_campus.domain.post.model.PostType;
import NotFound.next_campus.domain.post.service.PostService;
import NotFound.next_campus.global.auth.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @RequestBody PostDTO.CreateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
            ) {
        Long postId = postService.savePost(request, userDetails);

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
            @RequestParam("files") List<MultipartFile> files,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        postService.savePostImages(postId, files, userDetails);

        return ResponseEntity.ok().body(
                "이미지 등록 성공"
        );
    }

    @PatchMapping("/{post_id}")
    public ResponseEntity<String> modifyPost(
            @PathVariable("post_id") Long postId,
            @RequestBody PostDTO.UpdateContentRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        postService.updatePost(postId, request, userDetails);

        return ResponseEntity.ok().body(
                "게시물 수정 성공"
        );
    }

    @PatchMapping("/{post_id}/images")
    public ResponseEntity<String> modifyPostImage(
            @PathVariable("post_id") Long postId,
            @RequestParam("files") List<MultipartFile> files,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        postService.updatePostImages(postId, files, userDetails);

        return ResponseEntity.ok().body(
                "게시물 이미지 수정 성공"
        );
    }

    @PatchMapping("/update")
    public ResponseEntity<String> modifyPosts(
            @RequestBody PostDTO.UpdateStatusRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        postService.updateStatusOfPosts(request, userDetails);

        return ResponseEntity.ok().body(
                "게시물 인계 여부 일괄 수정 성공"
        );
    }

    @DeleteMapping("/{post_id}")
    public ResponseEntity<String> removePost(
            @PathVariable("post_id") Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        postService.deletePost(postId, userDetails);

        return ResponseEntity.ok().body(
                "게시물 삭제 성공"
        );
    }

    @PostMapping("/delete")
    public ResponseEntity<String> removePosts(
            @RequestBody PostDTO.DeleteRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        postService.deletePosts(request.getPostIds(), userDetails);

        return ResponseEntity.ok().body(
                "게시물 일괄 삭제 성공"
        );
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<PostDTO.Response> getPost(
            @PathVariable("post_id") Long postId
    ) {
        return ResponseEntity.ok().body(
                postService.getPostById(postId)
        );
    }

    @GetMapping
    public ResponseEntity<List<PostDTO.Response>> getAllPosts(
            @PageableDefault(page = 1) Pageable pageable,
            @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo
    ) {
        pageNo = (pageNo == 0) ? 0 : pageNo - 1;

        return ResponseEntity.ok().body(
                postService.getAllPostList(pageable, pageNo)
        );
    }

    @GetMapping("tags")
    public ResponseEntity<List<PostDTO.Response>> getPostsByTags(
            @RequestParam(value = "status", required = false) PostStatus status,
            @RequestParam(value = "type", required = false) PostType type,
            @RequestParam(value = "location_id", required = false) Long locationId,
            @RequestParam(value = "category_id", required = false) Long categoryId,
            @PageableDefault(page = 1) Pageable pageable,
            @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo
    ) {
        pageNo = (pageNo == 0) ? 0 : pageNo - 1;

        return ResponseEntity.ok().body(
                postService.getPostsByTags(status, type, locationId, categoryId,
                        pageable, pageNo)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostDTO.Response>> getPostsByKeyword(
            @RequestParam("keyword") String keyword,
            @PageableDefault(page = 1) Pageable pageable,
            @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo
    ) {
        pageNo = (pageNo == 0) ? 0 : pageNo - 1;

        return ResponseEntity.ok().body(
                postService.getPostsByKeyword(keyword, pageable, pageNo)
        );
    }

    @GetMapping("my")
    public ResponseEntity<List<PostDTO.Response>> getMyPosts(
            @PageableDefault(page = 1) Pageable pageable,
            @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        pageNo = (pageNo == 0) ? 0 : pageNo - 1;

        return ResponseEntity.ok().body(
                postService.getMyPosts(pageable, pageNo, userDetails)
        );
    }
}
