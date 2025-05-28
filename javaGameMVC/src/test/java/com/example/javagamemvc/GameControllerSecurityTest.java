package com.example.javagamemvc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GameControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/game - dostęp dla zalogowanego użytkownika")
    @WithMockUser(username = "user", authorities = {"USER"})
    void getAllGames_AsUser_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/game"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /api/game - brak dostępu dla USER")
    @WithMockUser(username = "user", authorities = {"USER"})
    void createGame_AsUser_ShouldReturnForbidden() throws Exception {
        String gameJson = """
        {
          "title": "Kingdom Come Deliverance 2",
          "description": "second release of medieval realistic rpg",
          "release_date": "2025-02-21",
          "like_number": 0,
          "liked_by_users": [],
          "genres": [
            {
              "id": 1,
              "name": "rpg"
            }
          ],
          "studios": [
            {
              "id": 5,
              "name": "Warhorse Studio"
            }
          ]
        }
        """;

        mockMvc.perform(post("/api/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameJson))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("POST /api/game - dostęp dla ADMIN")
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void createGame_AsAdmin_ShouldReturnOk() throws Exception {
        String gameJson = """
        {
          "title": "Kingdom Come Deliverance 2",
          "description": "second release of medieval realistic rpg",
          "release_date": "2025-02-21",
          "like_number": 0,
          "liked_by_users": [],
          "genres": [
            {
              "id": 1,
              "name": "rpg"
            }
          ],
          "studios": [
            {
              "id": 5,
              "name": "Warhorse Studio"
            }
          ]
        }
        """;

        mockMvc.perform(post("/api/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameJson))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/game/{id} - brak dostępu dla USER")
    @WithMockUser(username = "user", authorities = {"USER"})
    void deleteGame_AsUser_ShouldReturnForbidden() throws Exception {
        mockMvc.perform(delete("/api/game/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("DELETE /api/game/{id} - dostęp dla ADMIN")
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void deleteGame_AsAdmin_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/game/1"))
                .andExpect(status().isNoContent());
    }
}
