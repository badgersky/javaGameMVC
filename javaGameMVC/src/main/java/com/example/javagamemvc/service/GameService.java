package com.example.javagamemvc.service;

import com.example.javagamemvc.entity.Game;
import com.example.javagamemvc.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    public Optional<Game> findById(Long id) {
        return gameRepository.findById(id);
    }

    public Game save(Game game) {
        return gameRepository.save(game);
    }

    public Game update(Long id, Game updatedGame) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with id " + id));

        game.setTitle(updatedGame.getTitle());
        game.setDescription(updatedGame.getDescription());
        game.setRelease_date(updatedGame.getRelease_date());
        game.setLike_number(updatedGame.getLike_number());
        game.setGenres(updatedGame.getGenres());
        game.setStudios(updatedGame.getStudios());
        game.setLiked_by_users(updatedGame.getLiked_by_users());

        return gameRepository.save(game);
    }

    public void deleteById(Long id) {
        gameRepository.deleteById(id);
    }

    public List<Game> findGamesByStudioId(Long studioId) {
        return gameRepository.findByStudiosId(studioId);
    }
}
