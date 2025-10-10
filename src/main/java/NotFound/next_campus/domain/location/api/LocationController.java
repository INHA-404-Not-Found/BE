package NotFound.next_campus.domain.location.controller;

import NotFound.next_campus.domain.location.dto.LocationRequestDTO;
import NotFound.next_campus.domain.location.dto.LocationResponseDTO;
import NotFound.next_campus.domain.location.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    // 위치 생성
    @PostMapping
    public ResponseEntity<LocationResponseDTO> createLocation(@RequestBody LocationRequestDTO requestDTO) {
        return ResponseEntity.ok(locationService.createLocation(requestDTO));
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
    @PutMapping("/{id}")
    public ResponseEntity<LocationResponseDTO> updateLocation(@PathVariable Long id,
                                                              @RequestBody LocationRequestDTO requestDTO) {
        return ResponseEntity.ok(locationService.updateLocation(id, requestDTO));
    }

    // 위치 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
}
