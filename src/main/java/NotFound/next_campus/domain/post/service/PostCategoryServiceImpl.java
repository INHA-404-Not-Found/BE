package NotFound.next_campus.domain.post.service;

import NotFound.next_campus.domain.category.model.Category;
import NotFound.next_campus.domain.category.repository.CategoryRepository;
import NotFound.next_campus.domain.post.model.PostCategory;
import NotFound.next_campus.domain.post.model.Post;
import NotFound.next_campus.domain.post.repository.PostCategoryRepository;
import NotFound.next_campus.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCategoryServiceImpl implements PostCategoryService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final PostCategoryRepository itemRepository;

    @Override
    public void savePostCategories(Long postId, List<Long> categories) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

        List<Category> categoryList = categoryRepository.findAllById(categories);

        for (Category c : categoryList) {

            itemRepository.save(PostCategory.builder()
                    .post(post)
                    .category(c)
                    .build());
        }
    }
}
