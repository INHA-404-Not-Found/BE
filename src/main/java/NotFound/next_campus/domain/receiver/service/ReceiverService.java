package NotFound.next_campus.domain.receiver.service;

import NotFound.next_campus.domain.receiver.dto.ReceiverDTO;
import NotFound.next_campus.global.auth.user.CustomUserDetails;

import java.util.List;

public interface ReceiverService {

    /* 수령인 등록 */
    Long saveReceiver(ReceiverDTO.CreateRequest dto, CustomUserDetails userDetails);

    /* 수령인 정보 수정 */
    void updateReceiver(Long receiverId, ReceiverDTO.UpdateRequest dto, CustomUserDetails userDetails);

    /* 수령인 삭제 */
    void deleteReceiver(Long receiverId, CustomUserDetails userDetails);

    /* 수령인 상세 정보 조회 */
    ReceiverDTO.Response getReceiverInfo(Long receiverId, CustomUserDetails userDetails);

    /* 특정 게시물의 수령인 정보 조회 */
    ReceiverDTO.Response getReceiverByPost(Long postId, CustomUserDetails userDetails);

    /* 전체 수령인 목록 조회 */
    List<ReceiverDTO.Response> getAllReceivers(CustomUserDetails userDetails);
}
