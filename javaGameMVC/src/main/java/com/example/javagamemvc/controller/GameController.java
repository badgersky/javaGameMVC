package com.example.javagamemvc.controller;

import com.example.javagamemvc.entity.Game;
import com.example.javagamemvc.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game")
@Tag(name = "Game Controller", description = "Endpoints for managing games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    @Operation(summary = "Get all game")
    public ResponseEntity<List<Game>> getAllGenres() {
        return ResponseEntity.ok(gameService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get game by ID")
    public ResponseEntity<Game> getGenreById(@PathVariable Long id) {
        return gameService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create new game")
    public ResponseEntity<Game> createGenre(@RequestBody Game game) {
        return ResponseEntity.ok(gameService.save(game));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing game")
    public ResponseEntity<Game> updateGenre(@PathVariable Long id, @RequestBody Game updatedGame) {
        return ResponseEntity.ok(gameService.update(id, updatedGame));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete game by ID")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        gameService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/studio/{studioId}")
    @Operation(summary = "Get games by studio ID")
    public ResponseEntity<List<Game>> getGamesByStudioId(@PathVariable Long studioId) {
        List<Game> games = gameService.findGamesByStudioId(studioId);
        return ResponseEntity.ok(games);
    }
}

