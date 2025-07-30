package com.example.dreamjournalbackend.repository;

import com.example.dreamjournalbackend.model.Dream;
import com.example.dreamjournalbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DreamRepository extends JpaRepository<Dream, Long> {
    List<Dream> findByUser(User user);
}
