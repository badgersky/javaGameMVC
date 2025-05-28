package com.example.javagamemvc;

import com.example.javagamemvc.entity.Game;
import com.example.javagamemvc.repository.GameRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.time.LocalDate;
import java.util.HashSet;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class GameControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("test-db")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setup() {
        gameRepository.deleteAll();
    }

    @Test
    @WithMockUser
    void testGetAllGamesEmpty() throws Exception {
        mockMvc.perform(get("/api/game"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void testCreateGame() throws Exception {
        Game game = new Game();
        game.setTitle("Test Game");
        game.setDescription("Test Description");
        game.setRelease_date(LocalDate.of(2024, 5, 1));
        game.setLike_number(0);
        game.setGenres(new HashSet<>());
        game.setStudios(new HashSet<>());

        mockMvc.perform(post("/api/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(game)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Game"));
    }

    @Test
    @WithMockUser
    void testGetAllGames() throws Exception {
        Game game = new Game();
        game.setTitle("Game 1");
        game.setDescription("Desc");
        game.setRelease_date(LocalDate.now());
        gameRepository.save(game);

        mockMvc.perform(get("/api/game"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title").value("Game 1"));
    }

    @Test
    @WithMockUser
    void testGetGameById() throws Exception {
        Game game = new Game();
        game.setTitle("Game By Id");
        game.setDescription("Desc");
        game.setRelease_date(LocalDate.now());
        Game saved = gameRepository.save(game);

        mockMvc.perform(get("/api/game/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Game By Id"));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void testUpdateGame() throws Exception {
        Game game = new Game();
        game.setTitle("Old Title");
        game.setDescription("Old Desc");
        game.setRelease_date(LocalDate.now());
        Game saved = gameRepository.save(game);

        saved.setTitle("New Title");
        saved.setDescription("New Desc");

        mockMvc.perform(put("/api/game/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saved)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Title"))
                .andExpect(jsonPath("$.description").value("New Desc"));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void testDeleteGame() throws Exception {
        Game game = new Game();
        game.setTitle("To Delete");
        game.setDescription("Desc");
        game.setRelease_date(LocalDate.now());
        Game saved = gameRepository.save(game);

        mockMvc.perform(delete("/api/game/" + saved.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/game/" + saved.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testGetGamesByStudioId() throws Exception {
        mockMvc.perform(get("/api/game/studio/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testGetGamesByGenreId() throws Exception {
        mockMvc.perform(get("/api/game/genre/1"))
                .andExpect(status().isOk());
    }
}
