package com.example.javagamemvc.service;

import com.example.javagamemvc.entity.Game;
import com.example.javagamemvc.entity.Users;
import com.example.javagamemvc.repository.GameRepository;
import com.example.javagamemvc.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public UserService(UserRepository userRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    public List<Users> findAll() {
        return userRepository.findAll();
    }

    public Optional<Users> findById(Long id) {
        return userRepository.findById(id);
    }

    public Users save(Users user) {
        return userRepository.save(user);
    }

    public Users update(Long id, Users updatedUser) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setAccount_type(updatedUser.getAccount_type());
        user.setLiked_games(updatedUser.getLiked_games());
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public Users likeGame(Long userId, Long gameId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        if (user.getLiked_games().add(game)) {
            game.setLike_number(game.getLiked_by_users().size() + 1);
            gameRepository.save(game);
            userRepository.save(user);
        }

        return user;
    }

    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Users unlikeGame(Long userId, Long gameId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        if (user.getLiked_games().remove(game)) {
            game.setLike_number(game.getLiked_by_users().size() - 1);
            gameRepository.save(game);
            userRepository.save(user);
        }

        return user;
    }
}

