package com.aziq.JobJoy.Resume.Service;

import com.aziq.JobJoy.Resume.DTO.ResumeDto;
import com.aziq.JobJoy.Resume.Entity.Resume;
import com.aziq.JobJoy.Resume.Repository.ResumeRepository;
import com.aziq.JobJoy.User.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
            file.transferTo(new File(uploadDir + originalFilename));
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

//    public Resume saveResume(Resume resume) {
//        return resumeRepository.save(resume);
//    }

    public Resume getById(Long id){
        return resumeRepository.findById(id).get();
    }

    public void deleteById(Long id) {
        resumeRepository.deleteById(id);
    }

}
