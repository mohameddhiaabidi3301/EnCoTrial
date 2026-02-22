package tn.enicar.eniconnect.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.enicar.eniconnect.model.Event;
import tn.enicar.eniconnect.model.User;
import tn.enicar.eniconnect.repository.EventRepository;
import tn.enicar.eniconnect.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventService {

    private static final Logger logger = LogManager.getLogger(EventService.class);

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getUpcomingEvents() {
        return eventRepository.findByEventDateGreaterThanEqualOrderByEventDateAsc(LocalDate.now());
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé"));
    }

    public Event createEvent(Event event) {
        Event saved = eventRepository.save(event);
        logger.info("Événement créé : {} (id={})", saved.getTitle(), saved.getId());
        return saved;
    }

    public Event updateEvent(Long id, Event eventData) {
        Event event = getEventById(id);
        if (eventData.getTitle() != null)
            event.setTitle(eventData.getTitle());
        if (eventData.getDescription() != null)
            event.setDescription(eventData.getDescription());
        if (eventData.getEventDate() != null)
            event.setEventDate(eventData.getEventDate());
        if (eventData.getStartTime() != null)
            event.setStartTime(eventData.getStartTime());
        if (eventData.getEndTime() != null)
            event.setEndTime(eventData.getEndTime());
        if (eventData.getLocation() != null)
            event.setLocation(eventData.getLocation());
        if (eventData.getCategory() != null)
            event.setCategory(eventData.getCategory());
        if (eventData.getImageUrl() != null)
            event.setImageUrl(eventData.getImageUrl());
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
        logger.info("Événement supprimé (id={})", id);
    }

    public Event participate(Long eventId, Long userId) {
        Event event = getEventById(eventId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        event.getParticipants().add(user);
        logger.info("Participation ajoutée : user {} -> event {}", userId, eventId);
        return eventRepository.save(event);
    }

    public Event cancelParticipation(Long eventId, Long userId) {
        Event event = getEventById(eventId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        event.getParticipants().remove(user);
        return eventRepository.save(event);
    }
}
