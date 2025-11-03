package NotFound.next_campus.domain.location.service;

import NotFound.next_campus.domain.location.dto.LocationDTO;
import NotFound.next_campus.domain.location.model.Location;
import NotFound.next_campus.domain.location.repository.LocationRepository;
import NotFound.next_campus.domain.member.model.Role;
import NotFound.next_campus.global.auth.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    // 새로운 위치 등록
    public LocationDTO.Response createLocation(LocationDTO.CreateRequest requestDTO, CustomUserDetails userDetails) {

        // UserDetails에서 role 추출
        Role role = userDetails.getMember().getRole();

        if (role != Role.ADMIN) {
            throw new RuntimeException("권한이 없습니다.");
        }

        Location location = Location.builder()
                .name(requestDTO.getName())
                .build();

        Location saved = locationRepository.save(location);

        return new LocationDTO.Response(saved.getId(), saved.getName());
    }

    // 전체 위치 목록 조회
    public List<LocationDTO.Response> getAllLocations() {

        return locationRepository.findAll().stream()
                .map(l -> new LocationDTO.Response(l.getId(), l.getName()))
                .collect(Collectors.toList());
    }

    // 단일 위치 조회
    public LocationDTO.Response getLocation(Long id) {

        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 위치입니다."));

        return new LocationDTO.Response(location.getId(), location.getName());
    }

    // 위치 정보 수정
    public LocationDTO.Response updateLocation(Long id, LocationDTO.CreateRequest requestDTO, CustomUserDetails userDetails) {

        // 권한 체크
        if (!userDetails.getMember().getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("권한이 없습니다.");
        }

        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 위치입니다."));

        location.setName(requestDTO.getName());
        Location updated = locationRepository.save(location);

        return new LocationDTO.Response(updated.getId(), updated.getName());
    }

    // 위치 삭제
    public void deleteLocation(Long id, CustomUserDetails userDetails) {

        // 권한 체크
        if (!userDetails.getMember().getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("권한이 없습니다.");
        }

        locationRepository.deleteById(id);
    }
}
