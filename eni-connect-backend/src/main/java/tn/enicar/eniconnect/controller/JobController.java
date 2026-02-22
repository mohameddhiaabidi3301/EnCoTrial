package tn.enicar.eniconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.enicar.eniconnect.model.Job;
import tn.enicar.eniconnect.service.JobService;

import java.util.List;

/**
 * Contrôleur des offres d'emploi/stage — correspond au composant jobs Angular.
 */
@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*", maxAge = 3600)
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllActiveJobs());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Job>> getJobsByType(@PathVariable String type) {
        return ResponseEntity.ok(jobService.getJobsByType(type));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        return ResponseEntity.ok(jobService.createJob(job));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody Job jobData) {
        return ResponseEntity.ok(jobService.updateJob(id, jobData));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.ok().build();
    }
}
