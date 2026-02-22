package tn.enicar.eniconnect.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.enicar.eniconnect.model.Message;
import tn.enicar.eniconnect.model.User;
import tn.enicar.eniconnect.repository.MessageRepository;
import tn.enicar.eniconnect.repository.UserRepository;

import java.util.List;

@Service
public class MessageService {

    private static final Logger logger = LogManager.getLogger(MessageService.class);

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Message> getMessagesByThread(String threadId) {
        return messageRepository.findByThreadIdOrderByCreatedAtAsc(threadId);
    }

    public Message sendMessage(Long senderId, Long receiverId, String threadId,
            String threadType, String content) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Expéditeur non trouvé"));

        User receiver = null;
        if (receiverId != null) {
            receiver = userRepository.findById(receiverId).orElse(null);
        }

        Message message = Message.builder()
                .content(content)
                .sender(sender)
                .receiver(receiver)
                .threadId(threadId)
                .threadType(threadType)
                .build();

        Message saved = messageRepository.save(message);
        logger.info("Message envoyé par {} dans thread {}", sender.getFullName(), threadId);
        return saved;
    }

    public List<Message> getUserMessages(Long userId) {
        return messageRepository.findBySenderIdOrReceiverIdOrderByCreatedAtDesc(userId, userId);
    }

    public void markAsRead(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message non trouvé"));
        message.setIsRead(true);
        messageRepository.save(message);
    }
}
