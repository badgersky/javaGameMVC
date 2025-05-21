package com.example.javagamemvc.repository;

import com.example.javagamemvc.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
