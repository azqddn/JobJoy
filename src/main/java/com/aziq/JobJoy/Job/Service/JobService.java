package com.aziq.JobJoy.Job.Service;

import com.aziq.JobJoy.Job.DTO.JobDto;
import com.aziq.JobJoy.Job.Entity.Job;
import com.aziq.JobJoy.Job.Repository.JobRepository;
import com.aziq.JobJoy.Resume.Entity.Resume;
import com.aziq.JobJoy.User.Entity.User;
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

    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    public void saveJob(JobDto jobDto, User user, Resume resume){
        Job job = new Job();
        job.setJobTitle(jobDto.getJobTitle());
        job.setCompanyName(jobDto.getCompanyName());
        job.setLocation(jobDto.getLocation());
        job.setApplicationMethod(jobDto.getApplicationMethod());;
        job.setJobLink(jobDto.getJobLink());
        job.setHrEmail(jobDto.getHrEmail());
        job.setStatus(Job.Status.valueOf(jobDto.getStatus()));
        job.setApplicationDate(java.sql.Date.valueOf(jobDto.getApplicationDate()));
        job.setNotes(jobDto.getNotes());
        job.setResume(resume);
        job.setUser(user);

        jobRepository.save(job);
    }


    public void updateJob(Job job, JobDto jobDto, User user, Resume resume) {
        job.setJobTitle(jobDto.getJobTitle());
        job.setCompanyName(jobDto.getCompanyName());
        job.setLocation(jobDto.getLocation());
        job.setApplicationMethod(jobDto.getApplicationMethod());;
        job.setJobLink(jobDto.getJobLink());
        job.setHrEmail(jobDto.getHrEmail());
        job.setStatus(Job.Status.valueOf(jobDto.getStatus()));
        job.setApplicationDate(java.sql.Date.valueOf(jobDto.getApplicationDate()));
        job.setNotes(jobDto.getNotes());
        job.setResume(resume);
        job.setUser(user);

        jobRepository.save(job);
    }

    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }
}
