package NotFound.next_campus.global.config.auth;

import NotFound.next_campus.domain.member.model.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import NotFound.next_campus.domain.member.model.Member;
import NotFound.next_campus.domain.member.repository.MemberRepository;

@Configuration
public class DummyUserInitializer {

    @Bean
    CommandLineRunner initDummyUser(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (memberRepository.findByStudentId(00000001L).isEmpty()) {
                Member user = Member.builder()
                        .studentId(00000001L)
                        .name("admin2")
                        .email("admin2@gmail.com")
                        .department("none")
                        .role(Role.ADMIN)
                        .password(passwordEncoder.encode("1234"))
                        .build();

                memberRepository.save(user);
                System.out.println("Dummy ADMIN created successfully!");
            } else {
                System.out.println("Dummy ADMIN already exists, skipping initialization.");
            }

            // 일반 USER 계정 생성
            if (memberRepository.findByStudentId(20231234L).isEmpty()) {
                Member user = Member.builder()
                        .studentId(20231234L)
                        .name("studentA")
                        .email("studentA@inha.ac.kr")
                        .department("Software")
                        .role(Role.USER)
                        .password(passwordEncoder.encode("1234"))
                        .build();

                memberRepository.save(user);
                System.out.println("Dummy USER account created successfully!");
            } else {
                System.out.println("Dummy USER already exists, skipping initialization.");
            }
        };
    }
}