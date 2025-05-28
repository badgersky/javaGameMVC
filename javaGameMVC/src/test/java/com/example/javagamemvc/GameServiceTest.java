package com.example.javagamemvc;

import com.example.javagamemvc.entity.Game;
import com.example.javagamemvc.repository.GameRepository;
import com.example.javagamemvc.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServiceTest {

    private GameRepository gameRepository;
    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameRepository = mock(GameRepository.class);
        gameService = new GameService(gameRepository);
    }

    @Test
    void testFindAll() {
        Game game1 = new Game();
        game1.setTitle("Game One");

        Game game2 = new Game();
        game2.setTitle("Game Two");

        when(gameRepository.findAll()).thenReturn(Arrays.asList(game1, game2));

        List<Game> games = gameService.findAll();

        assertEquals(2, games.size());
        verify(gameRepository).findAll();
    }

    @Test
    void testFindAllEmpty() {
        when(gameRepository.findAll()).thenReturn(Collections.emptyList());

        List<Game> games = gameService.findAll();

        assertTrue(games.isEmpty());
        verify(gameRepository).findAll();
    }

    @Test
    void testSave() {
        Game game = new Game();
        game.setTitle("New Game");

        when(gameRepository.save(game)).thenReturn(game);

        Game savedGame = gameService.save(game);

        assertEquals("New Game", savedGame.getTitle());
        verify(gameRepository).save(game);
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        Game existingGame = new Game();
        existingGame.setTitle("Old Title");
        existingGame.setDescription("Old description");
        existingGame.setRelease_date(LocalDate.of(2020,1,1));
        existingGame.setLike_number(5);

        Game updatedGame = new Game();
        updatedGame.setTitle("Updated Title");
        updatedGame.setDescription("Updated description");
        updatedGame.setRelease_date(LocalDate.of(2021,2,2));
        updatedGame.setLike_number(10);

        when(gameRepository.findById(id)).thenReturn(Optional.of(existingGame));
        when(gameRepository.save(existingGame)).thenReturn(existingGame);

        Game result = gameService.update(id, updatedGame);

        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated description", result.getDescription());
        assertEquals(LocalDate.of(2021, 2, 2), result.getRelease_date());
        assertEquals(10, result.getLike_number());

        verify(gameRepository).findById(id);
        verify(gameRepository).save(existingGame);
    }

    @Test
    void testUpdateNotFound() {
        Long id = 2L;
        Game updatedGame = new Game();

        when(gameRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            gameService.update(id, updatedGame);
        });

        assertEquals("Game not found with id " + id, ex.getMessage());
        verify(gameRepository).findById(id);
    }

    @Test
    void testDeleteById() {
        Long id = 3L;

        doNothing().when(gameRepository).deleteById(id);

        gameService.deleteById(id);

        verify(gameRepository).deleteById(id);
    }

    @Test
    void testFindById() {
        Long id = 4L;
        Game game = new Game();
        game.setTitle("Some Game");

        when(gameRepository.findById(id)).thenReturn(Optional.of(game));

        Optional<Game> result = gameService.findById(id);

        assertTrue(result.isPresent());
        assertEquals("Some Game", result.get().getTitle());
        verify(gameRepository).findById(id);
    }

    @Test
    void testFindByIdNotFound() {
        Long id = 5L;

        when(gameRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Game> result = gameService.findById(id);

        assertFalse(result.isPresent());
        verify(gameRepository).findById(id);
    }

    @Test
    void testFindGamesByStudioId() {
        Long studioId = 1L;
        Game game1 = new Game();
        game1.setTitle("Studio Game 1");
        Game game2 = new Game();
        game2.setTitle("Studio Game 2");

        when(gameRepository.findByStudiosId(studioId)).thenReturn(Arrays.asList(game1, game2));

        List<Game> result = gameService.findGamesByStudioId(studioId);

        assertEquals(2, result.size());
        verify(gameRepository).findByStudiosId(studioId);
    }
}
