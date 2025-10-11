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
            if (memberRepository.findByEmail("doyun20445@gmail.com").isEmpty()) {
                Member user = Member.builder()
                        .studentId(12234063L)
                        .name("doyeon")
                        .email("doyun20445@gmail.com")
                        .department("CS")
                        .role(Role.valueOf("USER"))
                        .password(passwordEncoder.encode("1234"))
                        .build();

                memberRepository.save(user);
                System.out.println("Dummy user created successfully!");
            } else {
                System.out.println("Dummy user already exists, skipping initialization.");
            }
        };
    }
}