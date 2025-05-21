package com.example.javagamemvc.service;

import com.example.javagamemvc.entity.Genre;
import com.example.javagamemvc.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    public Optional<Genre> findById(Long id) {
        return genreRepository.findById(id);
    }

    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    public Genre update(Long id, Genre updatedGenre) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AccountType not found with id " + id));
        genre.setName(updatedGenre.getName());
        return genreRepository.save(genre);
    }

    public void deleteById(Long id) {
        genreRepository.deleteById(id);
    }
}
