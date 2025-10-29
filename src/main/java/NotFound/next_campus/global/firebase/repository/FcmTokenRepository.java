package NotFound.next_campus.global.firebase.repository;

import NotFound.next_campus.domain.member.model.Member;
import NotFound.next_campus.global.firebase.model.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {

    /* 같은 토큰이 있는지 검사 */
    Optional<FcmToken> findByToken(String token);

    @Query("SELECT f.token FROM FcmToken f " +
            "WHERE f.member = :member")
    List<String> findTokensByMember(@Param("member") Member member);

    void deleteByMember(Member member);
}
