package NotFound.next_campus.domain.post.service;

import java.util.List;

public interface PostCategoryService {

    void savePostCategories(Long postId, List<Long> categories);
}
