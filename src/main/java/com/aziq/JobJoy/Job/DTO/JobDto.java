package com.aziq.JobJoy.Job.DTO;


import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class JobDto {
    private int jobId;
    private int userId;
    private int resumeId;
    private int coverLetterId;
    private String jobTitle;
    private String companyName;
    private String location;
    private String applicationMethod;
    private String jobLink;
    private String hrEmail;
    private String status;
    private LocalDate applicationDate;
    private String notes;
    private Boolean isArchived;
    private Date createdAt;
    private Date updatedAt;

//    public enum Status {
//        Applied,
//        Interview,
//        Offer,
//        Rejected
//    }
}
