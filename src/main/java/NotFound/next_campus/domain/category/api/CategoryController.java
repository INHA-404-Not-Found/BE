package NotFound.next_campus.domain.category.api;

import NotFound.next_campus.domain.category.dto.request.CategoryRequestDTO;
import NotFound.next_campus.domain.category.dto.response.CategoryResponseDTO;
import NotFound.next_campus.domain.category.service.CategoryService;
import NotFound.next_campus.global.auth.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    // 카테고리 생성
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO requestDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
        CategoryResponseDTO response = categoryService.createCategory(requestDTO, userDetails);
        return ResponseEntity.ok(response);
    }

    // 전체 카테고리 조회
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    // 단일 카테고리 조회
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategory(id));
    }

    // 카테고리 수정
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id,
                                                              @RequestBody CategoryRequestDTO requestDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(categoryService.updateCategory(id, requestDTO, userDetails));
    }

    // 카테고리 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        categoryService.deleteCategory(id, userDetails);
        return ResponseEntity.noContent().build();
    }
}
