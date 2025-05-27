package com.example.javagamemvc.repository;

import com.example.javagamemvc.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByStudiosId(Long studioId);
}
