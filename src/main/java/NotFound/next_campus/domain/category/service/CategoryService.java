package NotFound.next_campus.domain.category.service;

import NotFound.next_campus.domain.category.dto.request.CategoryRequestDTO;
import NotFound.next_campus.domain.category.dto.response.CategoryResponseDTO;
import NotFound.next_campus.domain.category.model.Category;
import NotFound.next_campus.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 새로운 카테고리 생성
    public CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO, org.springframework.security.core.userdetails.User userDetails) {
        // UserDetails에서 role 추출
        String role = userDetails.getAuthorities().stream()
                .map(auth -> auth.getAuthority())  // "ROLE_ADMIN" 같은 형식
                .findFirst()
                .orElse("USER");

        //현재 로그인한 사용자의 이름과 role 로그로 확인
        System.out.println("현재 사용자: " + userDetails.getUsername());
        System.out.println("현재 권한들: " + userDetails.getAuthorities());

        // 권한 체크
        if (!role.equals("ADMIN") && !role.equals("ROLE_ADMIN")) {
            throw new AccessDeniedException("권한이 없습니다."); // 403으로 응답됨
        }

        // 카테고리 생성
        Category category = Category.builder()
                .name(requestDTO.getName())
                .build();

        Category saved = categoryRepository.save(category);
        return new CategoryResponseDTO(saved.getId(), saved.getName());
    }

    //모든 카테고리 조회
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(c -> new CategoryResponseDTO(c.getId(), c.getName()))
                .collect(Collectors.toList());
    }

    //특정 카테고리 조회
    public CategoryResponseDTO getCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));
        return new CategoryResponseDTO(category.getId(), category.getName());
    }

    //카테고리 수정
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO requestDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));
        category.setName(requestDTO.getName());
        Category updated = categoryRepository.save(category);
        return new CategoryResponseDTO(updated.getId(), updated.getName());
    }

    //카테고리 삭제
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}