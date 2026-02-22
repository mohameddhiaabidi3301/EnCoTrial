package tn.enicar.eniconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.enicar.eniconnect.model.Event;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByEventDateGreaterThanEqualOrderByEventDateAsc(LocalDate date);

    List<Event> findByCategoryOrderByEventDateAsc(String category);
}
