package NotFound.next_campus.domain.receiver.service;

import NotFound.next_campus.domain.receiver.dto.request.CreateReceiverDTO;
import NotFound.next_campus.domain.receiver.dto.request.UpdateReceiverDTO;
import NotFound.next_campus.domain.receiver.dto.response.ReceiverResponseDTO;
import NotFound.next_campus.global.auth.user.CustomUserDetails;

import java.util.List;

public interface ReceiverService {

    /* 수령인 등록 */
    Long saveReceiver(CreateReceiverDTO dto, CustomUserDetails userDetails);

    /* 수령인 정보 수정 */
    void updateReceiver(Long receiverId, UpdateReceiverDTO dto, CustomUserDetails userDetails);

    /* 수령인 삭제 */
    void deleteReceiver(Long receiverId, CustomUserDetails userDetails);

    /* 수령인 상세 정보 조회 */
    ReceiverResponseDTO getReceiverInfo(Long receiverId, CustomUserDetails userDetails);

    /* 특정 게시물의 수령인 정보 조회 */
    ReceiverResponseDTO getReceiverByPost(Long postId, CustomUserDetails userDetails);

    /* 전체 수령인 목록 조회 */
    List<ReceiverResponseDTO> getAllReceivers(CustomUserDetails userDetails);
}
