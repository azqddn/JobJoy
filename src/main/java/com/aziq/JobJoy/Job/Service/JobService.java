package com.aziq.JobJoy.Job.Service;

import com.aziq.JobJoy.Job.Entity.Job;
import com.aziq.JobJoy.Job.Repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public List<Job> getAllJobsOrderByApplicationDateDesc(){
        return jobRepository.findAllJobsOrderByApplicationDateDesc();
    }

    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }
}
