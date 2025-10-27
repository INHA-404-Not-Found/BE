package NotFound.next_campus.domain.location.service;

import NotFound.next_campus.domain.location.dto.request.LocationRequestDTO;
import NotFound.next_campus.domain.location.dto.response.LocationResponseDTO;
import NotFound.next_campus.domain.location.model.Location;
import NotFound.next_campus.domain.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    // 새로운 위치 등록
    public LocationResponseDTO createLocation(LocationRequestDTO requestDTO) {
        Location location = Location.builder()
                .name(requestDTO.getName())
                .build();

        Location saved = locationRepository.save(location);
        return new LocationResponseDTO(saved.getId(), saved.getName());
    }

    // 전체 위치 목록 조회
    public List<LocationResponseDTO> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(l -> new LocationResponseDTO(l.getId(), l.getName()))
                .collect(Collectors.toList());
    }

    // 단일 위치 조회
    public LocationResponseDTO getLocation(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 위치입니다."));
        return new LocationResponseDTO(location.getId(), location.getName());
    }

    // 위치 정보 수정
    public LocationResponseDTO updateLocation(Long id, LocationRequestDTO requestDTO) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 위치입니다."));

        location.setName(requestDTO.getName());
        Location updated = locationRepository.save(location);

        return new LocationResponseDTO(updated.getId(), updated.getName());
    }

    // 위치 삭제
    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }
}
