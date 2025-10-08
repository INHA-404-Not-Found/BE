package NotFound.next_campus.domain.category.repository;

import NotFound.next_campus.domain.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
