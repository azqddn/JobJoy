package com.aziq.JobJoy.User.DTO;

import com.aziq.JobJoy.User.Entity.User;
import lombok.Data;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Data
public class UserDto {
    private int userId;
    private String username;
    @NotEmpty(message = "Email is required")
    @Email(message = "Please provide a valid email")
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

//    @NotEmpty(message = "Confirm password is required")
//    private String confirmPassword;

    private String fullName;
    private String profilePictureUrl;
    private String linkedinUrl;
    private String portfolioUrl;
    private User.Role role;
    private Date createdAt;
    private Date updatedAt;
}
