package com.aziq.JobJoy.Cover_Letter.Service;

import com.aziq.JobJoy.Cover_Letter.DTO.CoverLetterDto;
import com.aziq.JobJoy.Cover_Letter.Entity.CoverLetter;
import com.aziq.JobJoy.Cover_Letter.Repository.CoverLetterRepository;
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
public class CoverLetterService {
    @Autowired
    private CoverLetterRepository coverLetterRepository;

    public List<CoverLetter> getAllCoverLetters() {
        return coverLetterRepository.findAll();
    }


    public String uploadCoverLetter(MultipartFile file, String uploadDir){
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

    //? Save the cover letter to database
    public void saveCoverLetter(CoverLetterDto coverLetterDto, String uniqueFileName, User user, MultipartFile file) {
        CoverLetter coverLetter = new CoverLetter();
        coverLetter.setTitle(coverLetterDto.getTitle());
        coverLetter.setUniqueFileName(uniqueFileName);
        coverLetter.setOriginalFileName(file.getOriginalFilename());
        coverLetter.setNotes(coverLetterDto.getNotes());
        coverLetter.setUserId(user);

        coverLetterRepository.save(coverLetter);
    }

    //? Find the cover letter by ID
    public CoverLetter getById(Long id) {

        return coverLetterRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id){
        coverLetterRepository.deleteById(id);
    }

    public void viewCoverLetter(String uploadDir, String filePath, HttpServletResponse response){
        Path coverLetterFile = Paths.get(uploadDir, filePath);
        if (Files.exists(coverLetterFile)) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=\"" + filePath + "\"");
            try {
                Files.copy(coverLetterFile, response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException e) {
                throw new RuntimeException("Error while serving the file", e);
            }
        } else {
            throw new RuntimeException("File not found");
        }
    }

    public void updateCoverLetter(Long id, CoverLetterDto coverLetterDto) {
        CoverLetter existingCoverLetter = coverLetterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cover Letter not found"));

        // Update only title and notes
        existingCoverLetter.setTitle(coverLetterDto.getTitle());
        existingCoverLetter.setNotes(coverLetterDto.getNotes());

        coverLetterRepository.save(existingCoverLetter);
    }

}
