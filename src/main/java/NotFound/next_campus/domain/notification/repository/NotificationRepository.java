package NotFound.next_campus.domain.notification.repository;

import NotFound.next_campus.domain.member.model.Member;
import NotFound.next_campus.domain.notification.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findByMember(Member member,
                                    Pageable pageable);
}
