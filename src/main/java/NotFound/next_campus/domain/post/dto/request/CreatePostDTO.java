package NotFound.next_campus.domain.post.dto.request;

import NotFound.next_campus.domain.post.model.PostType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostDTO {
    private Long memberId;          // 게시자
    private Long locationId;        // 발견 장소
    private String locationDetail;  // 세부 발견 장소
    private String title;           // 제목
    private String content;         // 내용
    private String storedLocation;  // 보관 위치
    private PostType type;          // 게시물 유형(분실/습득/공지)
    private Boolean isPersonal;     // 개인정보 포함 여부
}
