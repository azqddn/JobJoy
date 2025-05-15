package com.aziq.JobJoy.User.Controller;

import com.aziq.JobJoy.User.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String home() {
        return "User/home";
    }
}
