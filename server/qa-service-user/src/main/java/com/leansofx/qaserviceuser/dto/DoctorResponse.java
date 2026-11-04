package com.leansofx.qaserviceuser.dto;

import com.leansofx.qaserviceuser.entity.Doctor;
import java.util.List;

public record DoctorResponse(
    Long id,
    String username,
    String name,
    String title,
    String department,
    String avatar,
    String experience,
    List<String> specialties,
    Boolean isActive
) {
    public static DoctorResponse from(Doctor doctor) {
        return new DoctorResponse(
            doctor.getId(),
            doctor.getUsername(),
            doctor.getName(),
            doctor.getTitle(),
            doctor.getDepartment(),
            doctor.getAvatar(),
            doctor.getExperience(),
            doctor.getSpecialtiesList(),
            doctor.getIsActive()
        );
    }
}
