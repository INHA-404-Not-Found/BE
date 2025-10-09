package NotFound.next_campus.domain.receiver.service;

import NotFound.next_campus.domain.receiver.dto.request.CreateReceiverDTO;
import NotFound.next_campus.domain.receiver.dto.request.UpdateReceiverDTO;
import NotFound.next_campus.domain.receiver.dto.response.ReceiverResponseDTO;

import java.util.List;

public interface ReceiverService {

    /* 수령인 등록 */
    Long saveReceiver(CreateReceiverDTO dto);

    /* 수령인 정보 수정 */
    void updateReceiver(Long receiverId, UpdateReceiverDTO dto);

    /* 수령인 삭제 */
    void deleteReceiver(Long receiverId);

    /* 수령인 상세 정보 조회 */
    ReceiverResponseDTO getReceiverInfo(Long receiverId);

    /* 특정 게시물의 수령인 정보 조회 */
    ReceiverResponseDTO getReceiverByPost(Long postId);

    /* 전체 수령인 목록 조회 */
    List<ReceiverResponseDTO> getAllReceivers(Long memberId);
}
