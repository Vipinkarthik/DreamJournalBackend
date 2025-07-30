package com.example.dreamjournalbackend.dto;

import java.util.List;

public class DreamRequest {
    private String title;
    private String description;
    private String date;            // Expecting format YYYY-MM-DD
    private String mood;
    private List<String> tags;
    private boolean isLucid;
    private String userEmail;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getMood() { return mood; }
    public void setMood(String mood) { this.mood = mood; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public boolean isLucid() { return isLucid; }
    public void setLucid(boolean lucid) { isLucid = lucid; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
}
