package NotFound.next_campus.domain.post.dto.response;

import NotFound.next_campus.domain.post.model.Post;
import NotFound.next_campus.domain.post.model.PostStatus;
import NotFound.next_campus.domain.post.model.PostType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {
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

    public static PostResponseDTO from(Post post, List<String> categories, List<String> images) {

        String locationName = post.getLocation() == null ? null : post.getLocation().getName();

        return PostResponseDTO.builder()
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
