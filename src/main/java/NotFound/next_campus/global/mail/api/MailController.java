package NotFound.next_campus.global.mail.api;

import NotFound.next_campus.global.mail.service.MailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send")
    public String sendMail(@RequestParam String to,
                           @RequestParam String subject,
                           @RequestParam String content) {
        mailService.sendMail(to, subject, content);
        return "메일 전송 완료";
    }
}
