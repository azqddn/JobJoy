package com.aziq.JobJoy.Job.Mapper;

import com.aziq.JobJoy.Job.DTO.JobDto;
import com.aziq.JobJoy.Job.Entity.Job;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {
    public JobDto toDTO(Job job){
        JobDto dto = new JobDto();
        dto.setJobId(job.getJobId());
        dto.setUserId(job.getUser().getUserId());
        dto.setResumeId(job.getResume().getResumeId());
        dto.setJobTitle(job.getJobTitle());
        dto.setCompanyName(job.getCompanyName());
        dto.setLocation(job.getLocation());
        dto.setApplicationMethod(job.getApplicationMethod());
        dto.setJobLink(job.getJobLink());
        dto.setHrEmail(job.getHrEmail());
        dto.setStatus(job.getStatus().name());
        dto.setApplicationDate(job.getApplicationDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
        dto.setNotes(job.getNotes());
        dto.setIsArchived(job.getIsArchived());
        dto.setCreatedAt(job.getCreatedAt());
        dto.setUpdatedAt(job.getUpdatedAt());
        return dto;
    }
}
