package NotFound.next_campus.domain.receiver.api;

import NotFound.next_campus.domain.receiver.dto.request.CreateReceiverDTO;
import NotFound.next_campus.domain.receiver.dto.request.UpdateReceiverDTO;
import NotFound.next_campus.domain.receiver.dto.response.ReceiverResponseDTO;
import NotFound.next_campus.domain.receiver.service.ReceiverService;
import NotFound.next_campus.global.auth.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/receivers")
public class ReceiverController {

    private final ReceiverService receiverService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> registerReceiver(
            @RequestBody CreateReceiverDTO request,
            @AuthenticationPrincipal CustomUserDetails userDetails
            ) {
        Long receiverId = receiverService.saveReceiver(request, userDetails);

        return ResponseEntity.ok().body(
                Map.of(
                        "message", "수령인 등록 성공",
                        "receiverId", receiverId
                )
        );
    }

    @PatchMapping("/{receiver_id}")
    public ResponseEntity<String> updateReceiver(
            @PathVariable("receiver_id") Long receiverId,
            @RequestBody UpdateReceiverDTO request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        receiverService.updateReceiver(receiverId, request, userDetails);

        return ResponseEntity.ok().body(
                "수령인 수정 성공"
        );
    }

    @DeleteMapping("/{receiver_id}")
    public ResponseEntity<String> deleteReceiver(
            @PathVariable("receiver_id") Long receiverId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        receiverService.deleteReceiver(receiverId, userDetails);

        return ResponseEntity.ok().body(
                "수령인 삭제 성공"
        );
    }

    /* 특정 수령인 조회 */
    @GetMapping("/{receiver_id}")
    public ResponseEntity<ReceiverResponseDTO> getReceiver(
            @PathVariable("receiver_id") Long receiverId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok().body(
                receiverService.getReceiverInfo(receiverId, userDetails)
        );
    }

    /* 특정 게시물의 수령인 조회 */
    @GetMapping("/posts/{post_id}")
    public ResponseEntity<ReceiverResponseDTO> getReceiverByPost(
            @PathVariable("post_id") Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok().body(
                receiverService.getReceiverByPost(postId, userDetails)
        );
    }

    /* 모든 수령인 목록 조회 */
    @GetMapping
    public ResponseEntity<List<ReceiverResponseDTO>> getAllReceivers(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok().body(
                receiverService.getAllReceivers(userDetails)
        );
    }
}
