package com.example.javagamemvc;

import com.example.javagamemvc.entity.Game;
import com.example.javagamemvc.entity.Users;
import com.example.javagamemvc.repository.GameRepository;
import com.example.javagamemvc.repository.UserRepository;
import com.example.javagamemvc.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private GameRepository gameRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        gameRepository = mock(GameRepository.class);
        userService = new UserService(userRepository, gameRepository);
    }

    @Test
    @DisplayName("Should return all users")
    void testFindAll() {
        Users user1 = new Users();
        user1.setUsername("user1");

        Users user2 = new Users();
        user2.setUsername("user2");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<Users> users = userService.findAll();

        assertEquals(2, users.size());
        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Should find user by ID")
    void testFindById() {
        Users user = new Users();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<Users> result = userService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(userRepository).findById(1L);
    }

    @Test
    @DisplayName("Should save new user")
    void testSave() {
        Users user = new Users();
        user.setUsername("newuser");

        when(userRepository.save(user)).thenReturn(user);

        Users saved = userService.save(user);
        assertEquals("newuser", saved.getUsername());
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Should update existing user")
    void testUpdate() {
        Users existing = new Users();
        existing.setId(1L);
        existing.setUsername("old");
        existing.setEmail("old@example.com");

        Users updated = new Users();
        updated.setUsername("new");
        updated.setEmail("new@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.save(existing)).thenReturn(existing);

        Users result = userService.update(1L, updated);

        assertEquals("new", result.getUsername());
        assertEquals("new@example.com", result.getEmail());
        verify(userRepository).findById(1L);
        verify(userRepository).save(existing);
    }

    @Test
    @DisplayName("Should throw when updating non-existing user")
    void testUpdateUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        Users user = new Users();

        RuntimeException ex = assertThrows(RuntimeException.class, () -> userService.update(99L, user));
        assertEquals("User not found with id 99", ex.getMessage());
    }

    @Test
    @DisplayName("Should delete user by ID")
    void testDeleteById() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteById(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should like a game")
    void testLikeGame() {
        Users user = new Users();
        user.setId(1L);
        user.setLiked_games(new HashSet<>());

        Game game = new Game();
        game.setId(2L);
        game.setLiked_by_users(new HashSet<>());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(gameRepository.findById(2L)).thenReturn(Optional.of(game));
        when(gameRepository.save(any())).thenReturn(game);
        when(userRepository.save(any())).thenReturn(user);

        Users result = userService.likeGame(1L, 2L);

        assertTrue(result.getLiked_games().contains(game));
        verify(userRepository).save(user);
        verify(gameRepository).save(game);
    }

    @Test
    @DisplayName("Should unlike a game")
    void testUnlikeGame() {
        Game game = new Game();
        game.setId(2L);
        game.setLiked_by_users(new HashSet<>());

        Users user = new Users();
        user.setId(1L);
        Set<Game> liked = new HashSet<>();
        liked.add(game);
        user.setLiked_games(liked);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(gameRepository.findById(2L)).thenReturn(Optional.of(game));
        when(gameRepository.save(any())).thenReturn(game);
        when(userRepository.save(any())).thenReturn(user);

        Users result = userService.unlikeGame(1L, 2L);

        assertFalse(result.getLiked_games().contains(game));
        verify(userRepository).save(user);
        verify(gameRepository).save(game);
    }

    @Test
    @DisplayName("Should find user by username")
    void testFindByUsername() {
        Users user = new Users();
        user.setUsername("admin");

        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));

        Optional<Users> result = userService.findByUsername("admin");

        assertTrue(result.isPresent());
        assertEquals("admin", result.get().getUsername());
        verify(userRepository).findByUsername("admin");
    }
}
