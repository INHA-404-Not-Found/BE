package NotFound.next_campus.domain.post.service;

import NotFound.next_campus.domain.category.model.Category;
import NotFound.next_campus.domain.category.repository.CategoryRepository;
import NotFound.next_campus.domain.location.model.Location;
import NotFound.next_campus.domain.location.repository.LocationRepository;
import NotFound.next_campus.domain.member.model.Member;
import NotFound.next_campus.domain.member.model.Role;
import NotFound.next_campus.domain.member.repository.MemberRepository;
import NotFound.next_campus.domain.post.dto.request.CreatePostDTO;
import NotFound.next_campus.domain.post.dto.request.UpdatePostDTO;
import NotFound.next_campus.domain.post.model.Post;
import NotFound.next_campus.domain.post.model.PostCategory;
import NotFound.next_campus.domain.post.model.PostStatus;
import NotFound.next_campus.domain.post.model.PostType;
import NotFound.next_campus.domain.post.repository.PostCategoryRepository;
import NotFound.next_campus.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    private final MemberRepository memberRepository;
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;

    private final PostRepository postRepository;
    private final PostCategoryRepository postCategoryRepository;

    @Override
    public Long savePost(CreatePostDTO dto) {

        // 게시자
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Post post = Post.builder()
                .member(member)
                .title(dto.getTitle())
                .content(dto.getContent())
                .type(dto.getType())
                .isPersonal(dto.getIsPersonal())
                .build();

        // 게시물 상태가 POLICE 인 경우
        if(Role.USER.equals(member.getRole())
                && PostStatus.POLICE.equals(dto.getStatus())) {
            throw new IllegalArgumentException("인계 상태 등록 권한이 없습니다.");
        }

        // 완료/미완료/인계
        post.setStatus(dto.getStatus());

        // 공지사항인 경우
        if (PostType.NOTICE.equals(dto.getType())
                && !Role.ADMIN.equals(member.getRole())) {

            throw new IllegalArgumentException("공지는 관리자만 등록할 수 있습니다.");
        }

        // 습득 게시물인 경우
        if (PostType.FIND.equals(dto.getType())) {
            // 분실물 발견 위치
            Location location = locationRepository.findById(dto.getLocationId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 장소입니다."));

            post.setLocation(location);                         // 발견 장소
            post.setLocationDetail(dto.getLocationDetail());    // 세부 발견 장소
            post.setStoredLocation(dto.getStoredLocation());    // 보관장소
        }

        // 게시물 저장
        postRepository.save(post);

        savePostCategory(post, dto.getCategories());

        return post.getId();
    }

    @Override
    public void savePostImage(Long postId, MultipartFile file) {

        /* 권한 체크 로직 추가 */

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

        saveImage(post, file);
    }

    @Override
    public Long updatePost(Long postId, UpdatePostDTO dto) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (Role.USER.equals(member.getRole()) &&
                !post.getMember().equals(member)) {
            throw new IllegalArgumentException("해당 게시물에 대한 수정 권한이 없습니다.");
        }

        if (dto.getLocationDetail() != null) post.setLocationDetail(dto.getLocationDetail());
        if (dto.getTitle() != null) post.setTitle(dto.getTitle());
        if (dto.getContent() != null) post.setContent(dto.getContent());
        if (dto.getStoredLocation() != null) post.setStoredLocation(dto.getStoredLocation());
        if (dto.getIsPersonal() != null) post.setIsPersonal(dto.getIsPersonal());

        // 발견 위치 수정
        if (dto.getLocationId() != null) {

            Location location = locationRepository.findById(dto.getLocationId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 장소입니다."));

            post.setLocation(location);
        }

        // 게시물 상태 수정
        if(dto.getStatus() != null) {

            if(Role.USER.equals(member.getRole()) &&
                    PostStatus.POLICE.equals(dto.getStatus())) {
                throw new IllegalArgumentException("인계 상태 수정 권한이 없습니다.");
            }

            post.setStatus(dto.getStatus());
        }

        // 게시물 유형 수정
        if (dto.getType() != null) {

            if (Role.USER.equals(member.getRole()) &&
                    PostType.NOTICE.equals(dto.getType())) {
                throw new IllegalArgumentException("공지 게시 권한이 없습니다.");
            }

            post.setType(dto.getType());
        }

        // 게시물 카테고리 수정
        if (dto.getCategories() != null) {

            // 기존 카테고리 삭제
            postCategoryRepository.deleteByPost(post);
            
            // 새 카테고리 저장
            savePostCategory(post, dto.getCategories());
        }

        return post.getId();
    }

    @Override
    public void updatePostImage(Long postId, MultipartFile file) {

        /* 권한 체크 로직 추가 */

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

        deleteImage(post);
        saveImage(post, file);
    }

    @Override
    public void deletePost(Long postId, Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다 ."));

        if(Role.USER.equals(member.getRole()) &&
                !post.getMember().equals(member)) {
            throw new IllegalArgumentException("해당 게시물에 대한 삭제 권한이 없습니다.");
        }

        // 이미지 삭제
        deleteImage(post);
        // 게시물 카테고리 삭제
        postCategoryRepository.deleteByPost(post);
        // 게시물 삭제
        postRepository.delete(post);
    }

    private void savePostCategory(Post post, List<Long> categories) {

        // 게시물 카테고리 저장
        List<Category> categoryList = categoryRepository.findAllById(categories);

        for (Category c : categoryList) {

            postCategoryRepository.save(PostCategory.builder()
                    .post(post)
                    .category(c)
                    .build());
        }
    }

    private void saveImage(Post post, MultipartFile file) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("이미지가 존재하지 않습니다.");
        }

        String originalFileName = file.getOriginalFilename();
        String storedFileName = UUID.randomUUID() + "_" + originalFileName;

        String path = UPLOAD_DIR + storedFileName;

        try {
            File destination = new File(path);
            destination.getParentFile().mkdirs();
            file.transferTo(destination);
        } catch (IOException e) {
            throw new IllegalArgumentException("파일 저장 실패");
        }

        post.setOriginalFileName(originalFileName);
        post.setStoredFileName(storedFileName);
    }

    private void deleteImage(Post post) {

        String oldFileName = post.getStoredFileName();
        File oldFile = new File(UPLOAD_DIR + oldFileName);
        if(oldFile.exists()) {
            oldFile.delete();
        }
    }
}
