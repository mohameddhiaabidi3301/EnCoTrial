package tn.enicar.eniconnect.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.enicar.eniconnect.model.Job;
import tn.enicar.eniconnect.repository.JobRepository;

import java.util.List;

@Service
public class JobService {

    private static final Logger logger = LogManager.getLogger(JobService.class);

    @Autowired
    private JobRepository jobRepository;

    public List<Job> getAllActiveJobs() {
        return jobRepository.findByIsActiveTrueOrderByCreatedAtDesc();
    }

    public List<Job> getJobsByType(String jobType) {
        return jobRepository.findByJobTypeAndIsActiveTrueOrderByCreatedAtDesc(jobType);
    }

    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre non trouvée"));
    }

    public Job createJob(Job job) {
        Job saved = jobRepository.save(job);
        logger.info("Offre créée : {} chez {} (id={})", saved.getTitle(), saved.getCompanyName(), saved.getId());
        return saved;
    }

    public Job updateJob(Long id, Job jobData) {
        Job job = getJobById(id);
        if (jobData.getTitle() != null)
            job.setTitle(jobData.getTitle());
        if (jobData.getCompanyName() != null)
            job.setCompanyName(jobData.getCompanyName());
        if (jobData.getDescription() != null)
            job.setDescription(jobData.getDescription());
        if (jobData.getJobType() != null)
            job.setJobType(jobData.getJobType());
        if (jobData.getLocation() != null)
            job.setLocation(jobData.getLocation());
        if (jobData.getSalary() != null)
            job.setSalary(jobData.getSalary());
        if (jobData.getSkills() != null)
            job.setSkills(jobData.getSkills());
        return jobRepository.save(job);
    }

    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
        logger.info("Offre supprimée (id={})", id);
    }
}
