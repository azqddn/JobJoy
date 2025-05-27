package com.aziq.JobJoy.Resume.Service;

import com.aziq.JobJoy.Resume.DTO.ResumeDto;
import com.aziq.JobJoy.Resume.Entity.Resume;
import com.aziq.JobJoy.Resume.Repository.ResumeRepository;
import com.aziq.JobJoy.User.Entity.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ResumeService {
    @Autowired
    private ResumeRepository resumeRepository;

    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }

    public String uploadResume(MultipartFile file, String uploadDir) {
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFileName = UUID.randomUUID().toString() + extension;

        try {
            file.transferTo(new File(uploadDir + uniqueFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return uniqueFileName;
    }

    public void saveResume(ResumeDto resumeDto, String uniqueFileName, MultipartFile file, User user) {
        Resume resume = new Resume();
        resume.setTitle(resumeDto.getTitle());
        resume.setNotes(resumeDto.getNotes());
        resume.setUniqueFileName(uniqueFileName);
        resume.setOriginalFileName(file.getOriginalFilename());
        resume.setUser(user);

        resumeRepository.save(resume);
    }

    public Resume getById(Long id){
        return resumeRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        resumeRepository.deleteById(id);
    }

    public void viewResume(String uploadDir, String filePath, HttpServletResponse response){
        Path resumeFile = Paths.get(uploadDir, filePath);
        if (Files.exists(resumeFile)) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=\"" + filePath + "\"");
            try {
                Files.copy(resumeFile, response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException e) {
                throw new RuntimeException("Error while serving the file", e);
            }
        } else {
            throw new RuntimeException("File not found");
        }
    }

    public void updateResume(Long id, ResumeDto resumeDto) {
        Resume existingResume = resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found"));

        // Update only title and notes
        existingResume.setTitle(resumeDto.getTitle());
        existingResume.setNotes(resumeDto.getNotes());

        resumeRepository.save(existingResume);
    }

}
