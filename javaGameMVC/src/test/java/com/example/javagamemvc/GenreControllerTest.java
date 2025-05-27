package com.example.javagamemvc;

import com.example.javagamemvc.controller.GenreController;
import com.example.javagamemvc.entity.Genre;
import com.example.javagamemvc.service.GenreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GenreControllerTest {

    private GenreService genreService;
    private GenreController genreController;

    @BeforeEach
    void setUp() {
        genreService = mock(GenreService.class);
        genreController = new GenreController(genreService);
    }

    @Test
    void testGetAllGenres() {
        Genre genre1 = new Genre();
        genre1.setName("Action");
        Genre genre2 = new Genre();
        genre2.setName("RPG");

        when(genreService.findAll()).thenReturn(Arrays.asList(genre1, genre2));

        ResponseEntity<List<Genre>> response = genreController.getAllGenres();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(genreService).findAll();
    }

    @Test
    void testGetGenreByIdFound() {
        Genre genre = new Genre();
        genre.setName("Adventure");
        when(genreService.findById(1L)).thenReturn(Optional.of(genre));

        ResponseEntity<Genre> response = genreController.getGenreById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Adventure", response.getBody().getName());
    }

    @Test
    void testGetGenreByIdNotFound() {
        when(genreService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Genre> response = genreController.getGenreById(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testCreateGenre() {
        Genre inputGenre = new Genre();
        inputGenre.setName("Strategy");

        when(genreService.save(ArgumentMatchers.any(Genre.class))).thenReturn(inputGenre);

        ResponseEntity<Genre> response = genreController.createGenre(inputGenre);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Strategy", response.getBody().getName());
        verify(genreService).save(ArgumentMatchers.any(Genre.class));
    }

    @Test
    void testUpdateGenre() {
        Genre updatedGenre = new Genre();
        updatedGenre.setName("Simulation");

        when(genreService.update(eq(1L), any(Genre.class))).thenReturn(updatedGenre);

        ResponseEntity<Genre> response = genreController.updateGenre(1L, updatedGenre);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Simulation", response.getBody().getName());
        verify(genreService).update(eq(1L), any(Genre.class));
    }

    @Test
    void testDeleteGenre() {
        doNothing().when(genreService).deleteById(1L);

        ResponseEntity<Void> response = genreController.deleteGenre(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(genreService).deleteById(1L);
    }
}
