package com.example.dreamjournalbackend.service;

import com.example.dreamjournalbackend.dto.DreamRequest;
import com.example.dreamjournalbackend.dto.DreamResponse;
import com.example.dreamjournalbackend.model.Dream;
import com.example.dreamjournalbackend.model.User;
import com.example.dreamjournalbackend.repository.DreamRepository;
import com.example.dreamjournalbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.stream.Collectors;

@Service
public class DreamService {
    @Autowired private DreamRepository dreamRepository;
    @Autowired private UserRepository userRepository;

    public String createDream(DreamRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getUserEmail());
        if (userOpt.isEmpty()) {
            return "User not found";
        }

        Dream dream;
        try {
            dream = new Dream(
                    request.getTitle(),
                    request.getDescription(),
                    LocalDate.parse(request.getDate()),
                    request.getMood(),
                    request.getTags(),
                    request.isLucid(),
                    userOpt.get()
            );
        } catch (Exception ex) {
            return "Invalid date format";
        }

        dreamRepository.save(dream);
        return "Dream saved";
    }

    public List<DreamResponse> getDreamsByUserEmail(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return List.of();
        }

        return dreamRepository.findByUser(userOpt.get()).stream()
                .map(d -> new DreamResponse(
                        d.getId(),
                        d.getTitle(),
                        d.getDescription(),
                        d.getDate(),
                        d.getMood(),
                        d.getTags(),
                        d.isLucid(),
                        d.getCreatedAt()
                )).collect(Collectors.toList());
    }

    public void deleteDream(Long id) {
        dreamRepository.deleteById(id);
    }

    public Map<String, Object> getUserDreamStats(String userEmail) {
        Optional<User> userOpt = userRepository.findByEmail(userEmail);
        if (userOpt.isEmpty()) {
            return Map.of(
                "totalDreams", 0,
                "mostCommonMood", "No dreams yet",
                "frequentTags", List.of()
            );
        }
        User user = userOpt.get();
        List<Dream> userDreams = dreamRepository.findByUser(user);
        int totalDreams = userDreams.size();
        String mostCommonMood = userDreams.stream()
            .collect(Collectors.groupingBy(Dream::getMood, Collectors.counting()))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("No dreams yet");

        List<String> frequentTags = userDreams.stream()
            .filter(dream -> dream.getTags() != null && !dream.getTags().isEmpty())
            .flatMap(dream -> dream.getTags().stream())
            .filter(tag -> tag != null && !tag.trim().isEmpty())
            .map(String::trim)
            .collect(Collectors.groupingBy(tag -> tag, Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(5)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());

        return Map.of(
            "totalDreams", totalDreams,
            "mostCommonMood", mostCommonMood,
            "frequentTags", frequentTags
        );
    }
}
