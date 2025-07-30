package com.example.dreamjournalbackend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "dreams")
public class Dream {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDate date;
    private String mood;

    @ElementCollection
    private List<String> tags;

    private boolean isLucid;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Dream() {
        this.createdAt = LocalDateTime.now();
    }

    public Dream(String title, String description, LocalDate date, String mood,
                 List<String> tags, boolean isLucid, User user) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.mood = mood;
        this.tags = tags;
        this.isLucid = isLucid;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getMood() { return mood; }
    public void setMood(String mood) { this.mood = mood; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public boolean isLucid() { return isLucid; }
    public void setLucid(boolean lucid) { isLucid = lucid; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
