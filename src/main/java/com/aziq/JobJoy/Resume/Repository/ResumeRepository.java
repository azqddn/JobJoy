package com.aziq.JobJoy.Resume.Repository;

import com.aziq.JobJoy.Resume.Entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}
