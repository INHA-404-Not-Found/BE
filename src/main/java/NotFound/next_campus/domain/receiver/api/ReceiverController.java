package NotFound.next_campus.domain.receiver.api;

import NotFound.next_campus.domain.receiver.dto.request.CreateReceiverDTO;
import NotFound.next_campus.domain.receiver.dto.request.UpdateReceiverDTO;
import NotFound.next_campus.domain.receiver.dto.response.ReceiverResponseDTO;
import NotFound.next_campus.domain.receiver.service.ReceiverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/receivers")
public class ReceiverController {

    private final ReceiverService receiverService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> registerReceiver(
            @RequestBody CreateReceiverDTO request
    ) {
        Long receiverId = receiverService.saveReceiver(request);

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
            @RequestBody UpdateReceiverDTO request
    ) {
        receiverService.updateReceiver(receiverId, request);

        return ResponseEntity.ok().body(
                "수령인 수정 성공"
        );
    }

    @DeleteMapping("/{receiver_id}")
    public ResponseEntity<String> updateReceiver(
            @PathVariable("receiver_id") Long receiverId
    ) {
        receiverService.deleteReceiver(receiverId);

        return ResponseEntity.ok().body(
                "수령인 삭제 성공"
        );
    }

    @GetMapping("/{receiver_id}")
    public ResponseEntity<ReceiverResponseDTO> getReceiver(
            @PathVariable("receiver_id") Long receiverId
    ) {
        return ResponseEntity.ok().body(
                receiverService.getReceiverInfo(receiverId)
        );
    }

    @GetMapping("/posts/{post_id}")
    public ResponseEntity<ReceiverResponseDTO> getReceiverByPost(
            @PathVariable("post_id") Long postId
    ) {
        return ResponseEntity.ok().body(
                receiverService.getReceiverByPost(postId)
        );
    }

    @GetMapping
    public ResponseEntity<List<ReceiverResponseDTO>> getAllReceivers(
            @RequestParam("member") Long memberId
    ) {
        return ResponseEntity.ok().body(
                receiverService.getAllReceivers(memberId)
        );
    }
}
