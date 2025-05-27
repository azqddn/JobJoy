package com.aziq.JobJoy.Resume.Controller;


import com.aziq.JobJoy.Job.DTO.JobDto;
import com.aziq.JobJoy.Resume.DTO.ResumeDto;
import com.aziq.JobJoy.Resume.Entity.Resume;
import com.aziq.JobJoy.Resume.Service.ResumeService;
import com.aziq.JobJoy.User.Entity.User;
import com.aziq.JobJoy.User.Service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.UUID;

@RequestMapping("/resume")
@Controller
public class ResumeController {
    private final ResumeService resumeService;
    private final UserService userService;

    @Value("${resume.upload-dir}")
    private String uploadDir;

    @Autowired
    public ResumeController(ResumeService resumeService, UserService userService) {
        this.resumeService = resumeService;
        this.userService = userService;
    }

    @GetMapping("/list")
    public String getAllResumes(Model model) {
        model.addAttribute("resumes", resumeService.getAllResumes());
        return "Resume/resume-listing";
    }

    @GetMapping("/create")
    public String createResume(Model model) {
        Resume resume = new Resume();
        model.addAttribute("resume", resume);
        return "Resume/create-resume";
    }

    @PostMapping("/create")
    public String saveResume(@ModelAttribute ResumeDto resumeDto, Principal principal, @RequestParam("file") MultipartFile file )    {
        String uniqueFileName = resumeService.uploadResume(file, uploadDir);
        String email = principal.getName();
        User user = userService.findByEmail(email);

        resumeService.saveResume(resumeDto, uniqueFileName, file, user);

        return "redirect:/resume/list";
    }

    @GetMapping("/view/{filePath}")
    @ResponseBody
    public void viewFile(@PathVariable String filePath, HttpServletResponse response) {
        resumeService.viewResume(uploadDir, filePath, response);
    }

    @GetMapping("/edit/{id}")
    public String editResume(@PathVariable Long id, Model model) {
        Resume resume = resumeService.getById(id);
        model.addAttribute("resume", resume);
        return "Resume/edit-resume";
    }

    @PostMapping("/update/{id}")
    public String updateResume(@PathVariable Long id, @ModelAttribute ResumeDto resumeDto) {
        resumeService.updateResume(id, resumeDto);
        return "redirect:/resume/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteResume(@PathVariable Long id) {
        resumeService.deleteById(id);

        return "redirect:/resume/list";
    }
}
