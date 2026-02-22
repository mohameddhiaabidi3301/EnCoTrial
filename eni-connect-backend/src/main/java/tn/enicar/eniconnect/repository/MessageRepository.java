package tn.enicar.eniconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.enicar.eniconnect.model.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByThreadIdOrderByCreatedAtAsc(String threadId);

    List<Message> findBySenderIdOrReceiverIdOrderByCreatedAtDesc(Long senderId, Long receiverId);
}
