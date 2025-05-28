package com.aziq.JobJoy.Job.Entity;


import com.aziq.JobJoy.Cover_Letter.Entity.CoverLetter;
import com.aziq.JobJoy.Resume.Entity.Resume;
import com.aziq.JobJoy.User.Entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int jobId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_letter_id")
    private CoverLetter coverLetter;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "location")
    private String location;

    @Column(name = "application_method")
    private String applicationMethod;

    @Column(name = "job_link")
    private String jobLink;

    @Column(name = "hr_email")
    private String hrEmail;

    @Enumerated(EnumType.STRING)
    private Status status;
    public enum Status {
        Saved,
        Applied,
        Interview,
        Offer,
        Rejected,
        Accepted,
        Declined
    }

    @Column(name = "application_date")
    private Date applicationDate;

    @Column(name = "notes")
    private String notes;

    @Column(name = "is_archived", columnDefinition="BOOLEAN DEFAULT false")
    private Boolean isArchived;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;


}
