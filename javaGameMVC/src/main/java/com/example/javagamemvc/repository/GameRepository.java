package com.example.javagamemvc.repository;

import com.example.javagamemvc.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
