package com.aziq.JobJoy.Auth.Controller;

import com.aziq.JobJoy.User.DTO.UserDto;
import com.aziq.JobJoy.User.Entity.User;
import com.aziq.JobJoy.User.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "Auth/login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new UserDto());
        return "Auth/signup";
    }

    @PostMapping("/signup")
    public String registerUser(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {

        // Check for validation errors
        if (result.hasErrors()) {
            return "signup";
        }

        // Check if email already exists
        if (userService.existsByEmail(userDto.getEmail())) {
            model.addAttribute("emailError", "Email already registered");
            return "signup";
        }

        // Create and save new user
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(User.Role.ROLE_USER);

        userService.save(user);

        return "redirect:/login?registered";
    }
//    @GetMapping("/dashboard")
//    public String dashboard() {
//        return "dashboard";
//    }
}
