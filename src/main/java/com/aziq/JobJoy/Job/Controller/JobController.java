package com.aziq.JobJoy.Job.Controller;

import com.aziq.JobJoy.Job.DTO.JobDto;
import com.aziq.JobJoy.Job.Entity.Job;
import com.aziq.JobJoy.Job.Service.JobService;
import com.aziq.JobJoy.Resume.Entity.Resume;
import com.aziq.JobJoy.Resume.Service.ResumeService;
import com.aziq.JobJoy.User.Entity.User;
import com.aziq.JobJoy.User.Service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.logging.Logger;

@RequestMapping("/job")
@Controller
public class JobController {

    private final JobService jobService;
    private final UserService userService;
    private final ResumeService resumeService;

    @Value("${resume.upload-dir}")
    private String uploadDir;

    @Autowired
    public JobController(JobService jobService, UserService userService, ResumeService resumeService){
        this.jobService = jobService;
        this.userService = userService;
        this.resumeService = resumeService;
    }

    @GetMapping("/list")
    public String getAllJobs(Model model) {
        model.addAttribute("jobs", jobService.getAllJobsOrderByApplicationDateDesc());
//        model.addAttribute("resumes", resumeService.getAllResumes());
        return "Job/job-list";
    }

    @GetMapping("/create")
    public String createJob(Model model) {
//        Job job = new Job();
        model.addAttribute("job", new JobDto());
        model.addAttribute("resumes", resumeService.getAllResumes());
        return "Job/create-job";
    }

    @PostMapping("/create")
    public String saveJob(JobDto jobDto, Principal principal){

        Job job = new Job();
        job.setJobTitle(jobDto.getJobTitle());
        job.setCompanyName(jobDto.getCompanyName());
        job.setLocation(jobDto.getLocation());
        job.setApplicationMethod(jobDto.getApplicationMethod());;
        job.setJobLink(jobDto.getJobLink());
        job.setHrEmail(jobDto.getHrEmail());
        job.setStatus(Job.Status.valueOf(jobDto.getStatus()));
        job.setApplicationDate(java.sql.Date.valueOf(jobDto.getApplicationDate()));
        job.setNotes(jobDto.getNotes());

        Resume resume = resumeService.getById((long)jobDto.getResumeId());
        job.setResume(resume);

        String email = principal.getName();
        User user = userService.findByEmail(email);
        job.setUser(user);

        jobService.saveJob(job);

        return "redirect:/job/list";
    }

    @GetMapping("/view/{filePath}")
    @ResponseBody
    public void viewFile(@PathVariable String filePath, HttpServletResponse response) {
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
}
