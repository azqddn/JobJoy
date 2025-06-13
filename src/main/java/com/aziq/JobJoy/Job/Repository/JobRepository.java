package com.aziq.JobJoy.Job.Repository;

import com.aziq.JobJoy.Job.Entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    @Query("SELECT j FROM Job j ORDER BY j.updatedAt DESC")
    List<Job> findAllJobsOrderByApplicationDateDesc();
}
