package tn.enicar.eniconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.enicar.eniconnect.model.Job;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByIsActiveTrueOrderByCreatedAtDesc();

    List<Job> findByJobTypeAndIsActiveTrueOrderByCreatedAtDesc(String jobType);
}
