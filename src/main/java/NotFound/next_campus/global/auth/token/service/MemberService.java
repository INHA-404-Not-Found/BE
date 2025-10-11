package NotFound.next_campus.global.auth.token.service;

import NotFound.next_campus.domain.member.model.Member;
import NotFound.next_campus.domain.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Optional;

/**
 * 기존 Member 엔티티/레포지토리와 연결하는 서비스
 * - UserDetailsService 구현으로 Spring Security가 사용
 * - refresh token 저장/조회/삭제 유틸 제공
 */
@Service
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    // Spring Security가 인증 시 호출하는 메서드
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));


        // 엔티티의 role을 이용해 UserDetails 생성 (간단히 문자열 role 사용)
        return org.springframework.security.core.userdetails.User
                .withUsername(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole() == null ? "USER" : member.getRole().name())
                .build();
    }


    // refresh token 저장: expiry는 ISO_INSTANT 문자열로 저장
    public void saveRefreshToken(String email, String refreshToken, String expiryIsoString) {
        Optional<Member> opt = memberRepository.findByEmail(email);
        if (opt.isEmpty()) return;
        Member m = opt.get();
        m.setRefreshToken(refreshToken);
        m.setRefreshExpiry(expiryIsoString);
        memberRepository.save(m);
    }


    public String getRefreshToken(String email) {
        return memberRepository.findByEmail(email).map(Member::getRefreshToken).orElse(null);
    }


    public String getRefreshExpiry(String email) {
        return memberRepository.findByEmail(email).map(Member::getRefreshExpiry).orElse(null);
    }


    public void clearRefreshToken(String email) {
        Optional<Member> opt = memberRepository.findByEmail(email);
        if (opt.isEmpty()) return;
        Member m = opt.get();
        m.setRefreshToken(null);
        m.setRefreshExpiry(null);
        memberRepository.save(m);
    }


    // refreshExpiry 문자열을 Instant로 파싱 (null-safe)
    public Instant parseExpiry(String expiryIso) {
        if (expiryIso == null) return null;
        try {
            return Instant.parse(expiryIso);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}