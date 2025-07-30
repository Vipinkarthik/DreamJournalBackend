package com.example.dreamjournalbackend.controller;

import com.example.dreamjournalbackend.dto.LoginRequest;
import com.example.dreamjournalbackend.dto.RegisterRequest;
import com.example.dreamjournalbackend.model.User;
import com.example.dreamjournalbackend.service.AuthService;
import com.example.dreamjournalbackend.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        String result = authService.registerUser(request);
        if ("User registered successfully".equals(result)) {
            return ResponseEntity.ok(Map.of("message", result));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", result));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        Optional<User> userOpt = authService.loginUser(request);
        if (userOpt.isPresent()) {
            String token = jwtUtil.generateToken(userOpt.get().getEmail());
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<?> getUserDetails(@PathVariable String email, @RequestHeader(value = "Authorization", required = false) String authHeader) {
        String actualUserEmail = email;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7);
                String emailFromToken = jwtUtil.extractUsername(token);
                if (emailFromToken != null && !emailFromToken.isEmpty()) {
                    actualUserEmail = emailFromToken;
                }
            } catch (Exception e) {

            }
        }

        Optional<User> userOpt = authService.getUserByEmail(actualUserEmail);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Map<String, Object> userDetails = Map.of(
                "username", user.getUsername(),
                "email", user.getEmail(),
                "joined", user.getCreatedAt() != null ? user.getCreatedAt().toLocalDate().toString() : "Unknown"
            );
            return ResponseEntity.ok(userDetails);
        } else {
            return ResponseEntity.status(404).body(Map.of("message", "User not found"));
        }
    }
}
