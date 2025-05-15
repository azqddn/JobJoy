package com.aziq.JobJoy.Resume.Mapper;

import com.aziq.JobJoy.Resume.DTO.ResumeDto;
import com.aziq.JobJoy.Resume.Entity.Resume;
import org.springframework.stereotype.Component;

@Component
public class ResumeMapper {
    public ResumeDto toDTO(Resume resume) {
        ResumeDto dto = new ResumeDto();
        dto.setResumeId(resume.getResumeId());
        dto.setTitle(resume.getTitle());
        dto.setUniqueFileName(resume.getUniqueFileName());
        dto.setOriginalFileName(resume.getOriginalFileName());
        dto.setNotes(resume.getNotes());
        dto.setCreatedAt(resume.getCreatedAt());
        dto.setUpdatedAt(resume.getUpdatedAt());
        return dto;
    }
}
