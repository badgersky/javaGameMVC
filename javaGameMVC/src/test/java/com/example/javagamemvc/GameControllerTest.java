package com.example.javagamemvc;

import com.example.javagamemvc.controller.GameController;
import com.example.javagamemvc.entity.Game;
import com.example.javagamemvc.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameControllerTest {

    private GameService gameService;
    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameService = mock(GameService.class);
        gameController = new GameController(gameService);
    }

    @Test
    void testGetAllGames() {
        Game game1 = new Game();
        game1.setTitle("Game One");
        Game game2 = new Game();
        game2.setTitle("Game Two");

        when(gameService.findAll()).thenReturn(Arrays.asList(game1, game2));

        ResponseEntity<List<Game>> response = gameController.getAllGenres();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(gameService).findAll();
    }

    @Test
    void testGetGameByIdFound() {
        Game game = new Game();
        game.setTitle("My Game");
        when(gameService.findById(1L)).thenReturn(Optional.of(game));

        ResponseEntity<Game> response = gameController.getGenreById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("My Game", response.getBody().getTitle());
    }

    @Test
    void testGetGameByIdNotFound() {
        when(gameService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Game> response = gameController.getGenreById(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testCreateGame() {
        Game inputGame = new Game();
        inputGame.setTitle("New Game");
        inputGame.setRelease_date(LocalDate.of(2023, 1, 1));

        when(gameService.save(ArgumentMatchers.any(Game.class))).thenReturn(inputGame);

        ResponseEntity<Game> response = gameController.createGenre(inputGame);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("New Game", response.getBody().getTitle());
        verify(gameService).save(ArgumentMatchers.any(Game.class));
    }

    @Test
    void testUpdateGame() {
        Game updatedGame = new Game();
        updatedGame.setTitle("Updated Game");

        when(gameService.update(eq(1L), any(Game.class))).thenReturn(updatedGame);

        ResponseEntity<Game> response = gameController.updateGenre(1L, updatedGame);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated Game", response.getBody().getTitle());
        verify(gameService).update(eq(1L), any(Game.class));
    }

    @Test
    void testDeleteGame() {
        doNothing().when(gameService).deleteById(1L);

        ResponseEntity<Void> response = gameController.deleteGenre(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(gameService).deleteById(1L);
    }

    @Test
    void testGetGamesByStudioId() {
        Game game1 = new Game();
        game1.setTitle("Studio Game 1");
        Game game2 = new Game();
        game2.setTitle("Studio Game 2");

        when(gameService.findGamesByStudioId(10L)).thenReturn(Arrays.asList(game1, game2));

        ResponseEntity<List<Game>> response = gameController.getGamesByStudioId(10L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(gameService).findGamesByStudioId(10L);
    }
}
