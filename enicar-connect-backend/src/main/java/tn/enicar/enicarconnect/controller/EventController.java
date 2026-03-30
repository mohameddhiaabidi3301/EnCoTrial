package tn.enicar.enicarconnect.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tn.enicar.enicarconnect.dto.AppEventDTO;
import tn.enicar.enicarconnect.model.AppEvent;
import tn.enicar.enicarconnect.model.User;
import tn.enicar.enicarconnect.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Or configure via WebMvcConfigurer if applicable
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<AppEventDTO>> getAllEvents(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(eventService.getAllEvents(currentUser.getId()));
    }

    @PostMapping
    public ResponseEntity<AppEventDTO> createEvent(
            @RequestBody AppEvent eventData,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(eventService.createEvent(eventData, currentUser.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppEventDTO> updateEvent(
            @PathVariable Long id,
            @RequestBody AppEvent eventData,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(eventService.updateEvent(id, eventData, currentUser.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        eventService.deleteEvent(id, currentUser.getId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/toggle-register")
    public ResponseEntity<AppEventDTO> toggleRegister(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(eventService.toggleRegister(id, currentUser.getId()));
    }
}
