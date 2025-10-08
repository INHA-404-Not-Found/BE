package NotFound.next_campus.domain.location.repository;

import NotFound.next_campus.domain.location.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
