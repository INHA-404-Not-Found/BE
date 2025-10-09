package NotFound.next_campus.domain.member.repository;

import NotFound.next_campus.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
