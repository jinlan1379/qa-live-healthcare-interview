package com.leansofx.qaserviceuser.controller;

import com.leansofx.qaserviceuser.dto.LoginRequest;
import com.leansofx.qaserviceuser.dto.LoginResponse;
import com.leansofx.qaserviceuser.entity.Patient;
import com.leansofx.qaserviceuser.repository.PatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientRepository patientRepository;

    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return patientRepository.findByUsernameAndPassword(request.username(), request.password())
                .map(patient -> ResponseEntity.ok(
                        new LoginResponse(true, "登录成功", patientToMap(patient))))
                .orElse(ResponseEntity.ok(
                        new LoginResponse(false, "用户名或密码错误", null)));
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        String name = request.get("name");

        if (patientRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.ok(new LoginResponse(false, "用户名已存在", null));
        }

        Patient patient = new Patient();
        patient.setUsername(username);
        patient.setPassword(password);
        patient.setName(name);
        patient.setGender(request.getOrDefault("gender", ""));
        patient.setPhone(request.getOrDefault("phone", ""));

        Patient saved = patientRepository.save(patient);
        return ResponseEntity.ok(new LoginResponse(true, "注册成功", patientToMap(saved)));
    }

    private Map<String, Object> patientToMap(Patient p) {
        return Map.of(
                "id", p.getId(),
                "username", p.getUsername(),
                "name", p.getName(),
                "gender", p.getGender() != null ? p.getGender() : "",
                "phone", p.getPhone() != null ? p.getPhone() : ""
        );
    }
}
