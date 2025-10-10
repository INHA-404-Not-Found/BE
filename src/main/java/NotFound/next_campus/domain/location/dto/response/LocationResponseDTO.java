package NotFound.next_campus.domain.location.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LocationResponseDTO {
    private Long id;   // 위치 ID
    private String name; // 위치 이름
}
