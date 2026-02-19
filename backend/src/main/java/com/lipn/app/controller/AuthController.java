package com.lipn.app.controller;

import com.lipn.app.model.User;
import com.lipn.app.repository.UserRepository;
import com.lipn.app.security.JwtService;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    // ================= LOGIN =================
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User request) {

        User user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getEmail());

        return Map.of("token", token);
    }

    // ================= REGISTER SINGLE =================
    @PostMapping("/register")
    public User register(@RequestBody User request) {

        request.setPassword(encoder.encode(request.getPassword()));

        return repository.save(request);
    }

    // ================= REGISTER LIST =================
    @PostMapping("/register/batch")
    public List<User> registerBatch(@RequestBody List<User> users) {

        users.forEach(user ->
                user.setPassword(encoder.encode(user.getPassword()))
        );

        return repository.saveAll(users);
    }
}
