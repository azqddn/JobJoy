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

        Resume resume = resumeService.getById((long)jobDto.getResumeId());

        String email = principal.getName();
        User user = userService.findByEmail(email);

        jobService.saveJob(jobDto, user, resume);

        return "redirect:/job/list";
    }


    @GetMapping("/edit/{id}")
    public String editJob(@PathVariable int id, Model model) {

        model.addAttribute("job", jobService.getJobById((long) id));
        model.addAttribute("resumes", resumeService.getAllResumes());

        return "Job/edit-job";
    }

    @PostMapping("/update/{id}")
    public String updateJob(@ModelAttribute JobDto jobDto, @PathVariable int id, Principal principal) {

        System.out.println(jobDto);

        String email = principal.getName();
        User user = userService.findByEmail(email);

        Resume resume = resumeService.getById((long)jobDto.getResumeId());

        Job job = jobService.getJobById((long) id);
        jobService.updateJob(job, jobDto, user, resume);

        return "redirect:/job/list";
    }
}
