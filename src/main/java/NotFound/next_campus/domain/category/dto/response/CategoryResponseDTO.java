package NotFound.next_campus.domain.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoryResponseDTO {
    private Long id;    // 카테고리 ID
    private String name; // 카테고리 이름
}
