package NotFound.next_campus.domain.receiver.service;

import NotFound.next_campus.domain.member.model.Member;
import NotFound.next_campus.domain.member.model.Role;
import NotFound.next_campus.domain.member.repository.MemberRepository;
import NotFound.next_campus.domain.post.model.Post;
import NotFound.next_campus.domain.post.repository.PostRepository;
import NotFound.next_campus.domain.receiver.dto.request.CreateReceiverDTO;
import NotFound.next_campus.domain.receiver.dto.request.UpdateReceiverDTO;
import NotFound.next_campus.domain.receiver.dto.response.ReceiverResponseDTO;
import NotFound.next_campus.domain.receiver.model.Receiver;
import NotFound.next_campus.domain.receiver.repository.ReceiverRepository;
import NotFound.next_campus.global.auth.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReceiverServiceImpl implements ReceiverService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final ReceiverRepository receiverRepository;

    @Override
    public Long saveReceiver(CreateReceiverDTO dto, CustomUserDetails userDetails) {

        Member member = userDetails.getMember();

        if (!Role.ADMIN.equals(member.getRole())) {
            throw new AccessDeniedException("수령인 등록 권한이 없습니다.");
        }

        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

        Receiver receiver = receiverRepository.save(Receiver.builder()
                .post(post)
                .name(dto.getName())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .studentId(dto.getStudentId())
                .build());

        return receiver.getId();
    }

    @Override
    public void updateReceiver(Long receiverId, UpdateReceiverDTO dto, CustomUserDetails userDetails) {

        Member member = userDetails.getMember();

        if (!Role.ADMIN.equals(member.getRole())) {
            throw new AccessDeniedException("수령인 수정 권한이 없습니다.");
        }

        Receiver receiver = receiverRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 수령인입니다."));

        if(dto.getName() != null) receiver.setName(dto.getName());
        if(dto.getEmail() != null) receiver.setEmail(dto.getEmail());
        if(dto.getPhoneNumber() != null) receiver.setPhoneNumber(dto.getPhoneNumber());
        if(dto.getStudentId() != null) receiver.setStudentId(dto.getStudentId());
    }

    @Override
    public void deleteReceiver(Long receiverId, CustomUserDetails userDetails) {

        if(!Role.ADMIN.equals(userDetails.getRole())) {
            throw new AccessDeniedException("수령인 삭제 권한이 없습니다.");
        }

        Receiver receiver = receiverRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 수령인입니다."));

        receiverRepository.delete(receiver);
    }

    @Override
    public ReceiverResponseDTO getReceiverInfo(Long receiverId, CustomUserDetails userDetails) {

        if(!Role.ADMIN.equals(userDetails.getRole())) {
            throw new AccessDeniedException("수령인 조회 권한이 없습니다.");
        }

        Receiver receiver = receiverRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 수령인입니다."));

        return ReceiverResponseDTO.from(receiver);
    }

    @Override
    public ReceiverResponseDTO getReceiverByPost(Long postId, CustomUserDetails userDetails) {

        if(!Role.ADMIN.equals(userDetails.getRole())) {
            throw new AccessDeniedException("수령인 조회 권한이 없습니다.");
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

        Optional<Receiver> receiver = receiverRepository.findByPost(post);

        if(receiver.isPresent()) {

            return ReceiverResponseDTO.from(receiver.get());
        }

        return ReceiverResponseDTO.builder().build();
    }

    @Override
    public List<ReceiverResponseDTO> getAllReceivers(CustomUserDetails userDetails) {

        if(!Role.ADMIN.equals(userDetails.getRole())) {
            throw new AccessDeniedException("수령인 조회 권한이 없습니다.");
        }
        
        return receiverRepository.findAll().stream()
                .map(ReceiverResponseDTO::from)
                .toList();
    }
}
