package NotFound.next_campus.domain.location.api;

import NotFound.next_campus.domain.location.dto.request.LocationRequestDTO;
import NotFound.next_campus.domain.location.dto.response.LocationResponseDTO;
import NotFound.next_campus.domain.location.service.LocationService;
import NotFound.next_campus.global.auth.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    // 위치 생성
    @PostMapping
    public ResponseEntity<LocationResponseDTO> createLocation(@RequestBody LocationRequestDTO requestDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(locationService.createLocation(requestDTO, userDetails));
    }

    // 전체 위치 조회
    @GetMapping
    public ResponseEntity<List<LocationResponseDTO>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    // 단일 위치 조회
    @GetMapping("/{id}")
    public ResponseEntity<LocationResponseDTO> getLocation(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getLocation(id));
    }

    // 위치 수정
    @PatchMapping("/{id}")
    public ResponseEntity<LocationResponseDTO> updateLocation(@PathVariable Long id,
                                                              @RequestBody LocationRequestDTO requestDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(locationService.updateLocation(id, requestDTO, userDetails));
    }

    // 위치 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        locationService.deleteLocation(id, userDetails);
        return ResponseEntity.noContent().build();
    }
}
