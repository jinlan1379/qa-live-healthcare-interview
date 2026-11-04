package com.leansofx.qaserviceuser.controller;

import com.leansofx.qaserviceuser.dto.DoctorResponse;
import com.leansofx.qaserviceuser.dto.LoginRequest;
import com.leansofx.qaserviceuser.dto.LoginResponse;
import com.leansofx.qaserviceuser.repository.DoctorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorRepository doctorRepository;

    public DoctorController(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @GetMapping
    public List<DoctorResponse> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(DoctorResponse::from)
                .toList();
    }

    @GetMapping("/active")
    public List<DoctorResponse> getActiveDoctors() {
        return doctorRepository.findByIsActiveTrue().stream()
                .map(DoctorResponse::from)
                .toList();
    }

    @GetMapping("/{username}")
    public ResponseEntity<DoctorResponse> getDoctorByUsername(@PathVariable String username) {
        return doctorRepository.findByUsername(username)
                .map(DoctorResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return doctorRepository.findByUsernameAndPassword(request.username(), request.password())
                .map(doctor -> ResponseEntity.ok(
                        new LoginResponse(true, "登录成功", DoctorResponse.from(doctor))))
                .orElse(ResponseEntity.ok(
                        new LoginResponse(false, "用户名或密码错误", null)));
    }
}
