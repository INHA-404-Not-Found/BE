package NotFound.next_campus.domain.receiver.repository;

import NotFound.next_campus.domain.post.model.Post;
import NotFound.next_campus.domain.receiver.model.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReceiverRepository extends JpaRepository<Receiver, Long> {

    Optional<Receiver> findByPost(Post post);
}
