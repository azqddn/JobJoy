package com.aziq.JobJoy.Resume.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class ResumeDto {

    private int resumeId;
    private String title;
    private String uniqueFileName;
    private String originalFileName;
    private String notes;
    private Date createdAt;
    private Date updatedAt;

    private MultipartFile file;

}
