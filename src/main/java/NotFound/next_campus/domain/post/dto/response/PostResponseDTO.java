package NotFound.next_campus.domain.post.dto.response;

import NotFound.next_campus.domain.post.model.Post;
import NotFound.next_campus.domain.post.model.PostStatus;
import NotFound.next_campus.domain.post.model.PostType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String imagePath;
    private String storedLocation;
    private PostStatus status;
    private PostType type;
    private Boolean isPersonal;
    private List<String> categories;

    public static PostResponseDTO from(Post post, List<String> categories) {

        String locationName = post.getLocation() == null ? null : post.getLocation().getName();

        return PostResponseDTO.builder()
                .postId(post.getId())
                .writer(post.getMember().getName())
                .locationName(locationName)
                .locationDetail(post.getLocationDetail())
                .title(post.getTitle())
                .content(post.getContent())
                .imagePath("/uploads/" + post.getStoredFileName())
                .storedLocation(post.getStoredLocation())
                .status(post.getStatus())
                .type(post.getType())
                .isPersonal(post.getIsPersonal())
                .categories(categories)
                .build();
    }
}
