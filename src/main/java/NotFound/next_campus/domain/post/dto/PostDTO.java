package NotFound.next_campus.domain.post.dto;

import NotFound.next_campus.domain.post.model.Post;
import NotFound.next_campus.domain.post.model.PostStatus;
import NotFound.next_campus.domain.post.model.PostType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class PostDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRequest {
        // private Long memberId;          // 게시자
        private Long locationId;        // 발견 장소
        private String locationDetail;  // 세부 발견 장소
        private String title;           // 제목
        private String content;         // 내용
        private String storedLocation;  // 보관 위치
        private PostStatus status;
        private PostType type;          // 게시물 유형(분실/습득/공지)
        private Boolean isPersonal;     // 개인정보 포함 여부
        private String studentId;
        private List<Long> categories;  // 게시할 카테고리 목록
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteRequest {
        // private Long memberId;
        private List<Long> postIds;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateContentRequest {
        // private Long memberId;          // 게시자
        private Long locationId;        // 발견 장소
        private String locationDetail;  // 세부 발견 장소
        private String title;           // 제목
        private String content;         // 내용
        private String storedLocation;  // 보관 위치
        private PostStatus status;      // 완료 상태
        private PostType type;          // 게시물 유형(분실/습득/공지)
        private Boolean isPersonal;     // 개인정보 포함 여부
        private List<Long> categories;  // 게시할 카테고리 목록
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateStatusRequest {
        // private Long memberId;
        private List<Long> postIds;
        private PostStatus status;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long postId;
        private String writer;
        private String locationName;
        private String locationDetail;
        private String title;
        private String content;
        private List<String> imagePath;
        private String storedLocation;
        private PostStatus status;
        private PostType type;
        private Boolean isPersonal;
        private List<String> categories;
        private LocalDateTime createdAt;

        public static Response from(Post post, List<String> categories, List<String> images) {

            String locationName = post.getLocation() == null ? null : post.getLocation().getName();

            return Response.builder()
                    .postId(post.getId())
                    .writer(post.getMember().getName())
                    .locationName(locationName)
                    .locationDetail(post.getLocationDetail())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .imagePath(images)
                    .storedLocation(post.getStoredLocation())
                    .status(post.getStatus())
                    .type(post.getType())
                    .isPersonal(post.getIsPersonal())
                    .categories(categories)
                    .createdAt(post.getCreatedAt())
                    .build();
        }
    }
}
