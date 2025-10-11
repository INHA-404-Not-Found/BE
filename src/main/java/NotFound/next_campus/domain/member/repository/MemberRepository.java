package NotFound.next_campus.domain.member.repository;

import NotFound.next_campus.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 이메일로 회원 찾기 (로그인 시 필요)
    Optional<Member> findByEmail(String email);

    // refresh token으로 회원 찾기 (토큰 재발급 시 필요)
    Optional<Member> findByRefreshToken(String refreshToken);
}
