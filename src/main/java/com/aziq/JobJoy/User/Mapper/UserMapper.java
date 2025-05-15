package com.aziq.JobJoy.User.Mapper;

import com.aziq.JobJoy.User.DTO.UserDto;
import com.aziq.JobJoy.User.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDTO(User user) {
        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setProfilePictureUrl(user.getProfilePictureUrl());
        dto.setLinkedinUrl(user.getLinkedinUrl());
        dto.setPortfolioUrl(user.getPortfolioUrl());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}
