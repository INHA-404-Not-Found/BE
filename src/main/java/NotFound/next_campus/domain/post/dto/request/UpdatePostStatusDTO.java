package NotFound.next_campus.domain.post.dto.request;

import NotFound.next_campus.domain.post.model.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostStatusDTO {
    private Long memberId;
    private List<Long> postIds;
    private PostStatus status;
}
