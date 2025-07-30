package com.example.dreamjournalbackend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DreamResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate date;
    private String mood;
    private List<String> tags;
    private boolean isLucid;
    private LocalDateTime createdAt;

    public DreamResponse(Long id, String title, String description,
                         LocalDate date, String mood, List<String> tags,
                         boolean isLucid, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.mood = mood;
        this.tags = tags;
        this.isLucid = isLucid;
        this.createdAt = createdAt;
    }

public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDate getDate() { return date; }
    public String getMood() { return mood; }
    public List<String> getTags() { return tags; }
    public boolean isLucid() { return isLucid; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
