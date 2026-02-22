package tn.enicar.eniconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.enicar.eniconnect.model.Group;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findByCategoryOrderByCreatedAtDesc(String category);
}
