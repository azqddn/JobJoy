package com.aziq.JobJoy.Cover_Letter.Mapper;

import com.aziq.JobJoy.Cover_Letter.DTO.CoverLetterDto;
import org.springframework.stereotype.Component;

@Component
public class CoverLetterMapper {
    public CoverLetterDto toDTO(CoverLetterDto coverLetterDto){
        CoverLetterDto dto = new CoverLetterDto();
        dto.setCoverLetterId(coverLetterDto.getCoverLetterId());
        dto.setTitle(coverLetterDto.getTitle());
        dto.setUniqueFileName(coverLetterDto.getUniqueFileName());
        dto.setOriginalFileName(coverLetterDto.getOriginalFileName());
        dto.setNotes(coverLetterDto.getNotes());
        dto.setCreatedAt(coverLetterDto.getCreatedAt());
        dto.setUpdatedAt(coverLetterDto.getUpdatedAt());
        return dto;
    }
}
