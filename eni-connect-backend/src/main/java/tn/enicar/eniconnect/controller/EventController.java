package tn.enicar.eniconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tn.enicar.eniconnect.model.Event;
import tn.enicar.eniconnect.model.User;
import tn.enicar.eniconnect.service.EventService;

import java.util.List;

/**
 * Contrôleur des événements — correspond au composant events Angular.
 */
@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<Event>> getUpcomingEvents() {
        return ResponseEntity.ok(eventService.getUpcomingEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        return ResponseEntity.ok(eventService.createEvent(event));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event eventData) {
        return ResponseEntity.ok(eventService.updateEvent(id, eventData));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/participate")
    public ResponseEntity<Event> participate(@PathVariable Long id,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(eventService.participate(id, user.getId()));
    }

    @DeleteMapping("/{id}/participate")
    public ResponseEntity<Event> cancelParticipation(@PathVariable Long id,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(eventService.cancelParticipation(id, user.getId()));
    }
}
