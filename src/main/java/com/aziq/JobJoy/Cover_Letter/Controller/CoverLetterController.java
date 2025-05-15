package com.aziq.JobJoy.Cover_Letter.Controller;

import com.aziq.JobJoy.Cover_Letter.DTO.CoverLetterDto;
import com.aziq.JobJoy.Cover_Letter.Entity.CoverLetter;
import com.aziq.JobJoy.Cover_Letter.Service.CoverLetterService;
import com.aziq.JobJoy.User.Entity.User;
import com.aziq.JobJoy.User.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping("/cover-letter")
public class CoverLetterController {

    private final CoverLetterService coverLetterService;
    private final UserService userService;

    @Value("${cover-letter.upload-dir}")
    private String uploadDir;

    @Autowired
    public CoverLetterController(CoverLetterService coverLetterService, UserService userService){
        this.coverLetterService = coverLetterService;
        this.userService = userService;
    }



    // List all cover letters
    @GetMapping("/list")
    public String getAllCoverLetters(Model model){
        model.addAttribute("coverLetters", coverLetterService.getAllCoverLetters());
        return "Cover_Letter/cover-letter-listing";
    }
    // Display create form
    @GetMapping("/create")
    public String createCoverLetter(Model model){
        model.addAttribute("coverLetters", new CoverLetter());
        return "Cover_Letter/cover-letter-create";
    }

    // Save requested cover letter
    @PostMapping("/create")
    public String saveCoverLetter(Principal principal, @ModelAttribute CoverLetterDto coverLetterDto, @RequestParam("file") MultipartFile file){
        String uniqueFileName = coverLetterService.uploadCoverLetter(file, uploadDir);
        String email = principal.getName();
        User user = userService.findByEmail(email);

        coverLetterService.saveCoverLetter(coverLetterDto, uniqueFileName, user, file);

        return "redirect:/cover-letter/list";
    }

}
