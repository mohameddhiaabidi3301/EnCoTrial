package tn.enicar.eniconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tn.enicar.eniconnect.model.Message;
import tn.enicar.eniconnect.model.User;
import tn.enicar.eniconnect.service.MessageService;

import java.util.List;
import java.util.Map;

/**
 * Contrôleur des messages — correspond au chat Angular.
 */
@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * GET /api/messages/thread/{threadId} — Messages d'un thread.
     */
    @GetMapping("/thread/{threadId}")
    public ResponseEntity<List<Message>> getMessagesbyThread(@PathVariable String threadId) {
        return ResponseEntity.ok(messageService.getMessagesByThread(threadId));
    }

    /**
     * GET /api/messages/me — Tous les messages de l'utilisateur connecté.
     */
    @GetMapping("/me")
    public ResponseEntity<List<Message>> getMyMessages(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(messageService.getUserMessages(user.getId()));
    }

    /**
     * POST /api/messages — Envoyer un message.
     */
    @PostMapping
    public ResponseEntity<Message> sendMessage(@AuthenticationPrincipal User user,
            @RequestBody Map<String, String> body) {
        Long receiverId = body.get("receiverId") != null ? Long.parseLong(body.get("receiverId")) : null;

        Message message = messageService.sendMessage(
                user.getId(),
                receiverId,
                body.get("threadId"),
                body.get("threadType"),
                body.get("content"));
        return ResponseEntity.ok(message);
    }

    /**
     * PUT /api/messages/{id}/read — Marquer comme lu.
     */
    @PutMapping("/{id}/read")
    public ResponseEntity<?> markAsRead(@PathVariable Long id) {
        messageService.markAsRead(id);
        return ResponseEntity.ok().build();
    }
}
