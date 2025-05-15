package com.aziq.JobJoy.Cover_Letter.Service;

import com.aziq.JobJoy.Cover_Letter.DTO.CoverLetterDto;
import com.aziq.JobJoy.Cover_Letter.Entity.CoverLetter;
import com.aziq.JobJoy.Cover_Letter.Repository.CoverLetterRepository;
import com.aziq.JobJoy.User.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class CoverLetterService {
    @Autowired
    private CoverLetterRepository coverLetterRepository;

    public List<CoverLetter> getAllCoverLetters() {
        return coverLetterRepository.findAll();
    }

    //? Upload resume file and return the unique file name
    public String uploadCoverLetter(MultipartFile file, String uploadDir){
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

    //? Save the cover letter to database
    public void saveCoverLetter(CoverLetterDto coverLetterDto, String uniqueFileName, User user, MultipartFile file) {
        CoverLetter coverLetter = new CoverLetter();
        coverLetter.setTitle(coverLetterDto.getTitle());
        coverLetter.setUniqueFileName(uniqueFileName);
        //! Recheck this
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

}
