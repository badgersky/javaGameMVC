package com.example.javagamemvc.controller;

import com.example.javagamemvc.entity.Genre;
import com.example.javagamemvc.service.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
@Tag(name = "Genre Controller", description = "Endpoints for managing game genres")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    @Operation(summary = "Get all game genres")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Genre>> getAllGenres() {
        return ResponseEntity.ok(genreService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get game genre by ID")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Genre> getGenreById(@PathVariable Long id) {
        return genreService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create new genre")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Genre> createGenre(@RequestBody Genre genre) {
        return ResponseEntity.ok(genreService.save(genre));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing genre")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Genre> updateGenre(@PathVariable Long id, @RequestBody Genre updatedGenre) {
        return ResponseEntity.ok(genreService.update(id, updatedGenre));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete genre by ID")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        genreService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

