package com.example.dreamjournalbackend.service;

import com.example.dreamjournalbackend.dto.LoginRequest;
import com.example.dreamjournalbackend.dto.RegisterRequest;
import com.example.dreamjournalbackend.model.User;
import com.example.dreamjournalbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String registerUser(RegisterRequest request) {
        Optional<User> existing = userRepository.findByEmail(request.getEmail());
        if (existing.isPresent()) {
            return "Email already exists";
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return "User registered successfully";
    }

    public Optional<User> loginUser(LoginRequest request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()) {
            User foundUser = user.get();
            String storedPassword = foundUser.getPassword();

            if (storedPassword.startsWith("$2a$") || storedPassword.startsWith("$2b$") || storedPassword.startsWith("$2y$")) {
                if (passwordEncoder.matches(request.getPassword(), storedPassword)) {
                    return user;
                }
            } else {
                if (request.getPassword().equals(storedPassword)) {
                    // Update the password to be encoded
                    foundUser.setPassword(passwordEncoder.encode(request.getPassword()));
                    userRepository.save(foundUser);
                    return user;
                }
            }
        }
        return Optional.empty();
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
