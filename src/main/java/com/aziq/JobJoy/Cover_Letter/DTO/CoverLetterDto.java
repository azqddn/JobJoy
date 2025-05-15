package com.aziq.JobJoy.Cover_Letter.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CoverLetterDto {
    private int coverLetterId;
    private String title;
    private String uniqueFileName;
    private String originalFileName;
    private String notes;
    private String createdAt;
    private String updatedAt;

    private MultipartFile file;
}
