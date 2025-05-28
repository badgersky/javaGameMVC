package com.example.javagamemvc;

import com.example.javagamemvc.entity.Genre;
import com.example.javagamemvc.repository.GenreRepository;
import com.example.javagamemvc.service.GenreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GenreServiceTest {

    private GenreRepository genreRepository;
    private GenreService genreService;

    @BeforeEach
    void setUp() {
        genreRepository = mock(GenreRepository.class);
        genreService = new GenreService(genreRepository);
    }

    @Test
    void testFindAll() {
        Genre genre1 = new Genre();
        genre1.setName("Action");

        Genre genre2 = new Genre();
        genre2.setName("Adventure");

        when(genreRepository.findAll()).thenReturn(Arrays.asList(genre1, genre2));

        List<Genre> genres = genreService.findAll();

        assertEquals(2, genres.size());
        verify(genreRepository).findAll();
    }

    @Test
    void testFindAllEmpty() {
        when(genreRepository.findAll()).thenReturn(Collections.emptyList());

        List<Genre> genres = genreService.findAll();

        assertEquals(0, genres.size());
        verify(genreRepository).findAll();
    }

    @Test
    void testSaveGenre() {
        Genre genre = new Genre();
        genre.setName("RPG");

        when(genreRepository.save(genre)).thenReturn(genre);

        Genre savedGenre = genreService.save(genre);

        assertEquals("RPG", savedGenre.getName());
        verify(genreRepository).save(genre);
    }

    @Test
    void testUpdateGenre() {
        Long id = 1L;
        Genre existingGenre = new Genre();
        existingGenre.setName("Old Genre");

        Genre updatedGenre = new Genre();
        updatedGenre.setName("Updated Genre");

        when(genreRepository.findById(id)).thenReturn(Optional.of(existingGenre));
        when(genreRepository.save(existingGenre)).thenReturn(existingGenre);

        Genre result = genreService.update(id, updatedGenre);

        assertEquals("Updated Genre", result.getName());
        verify(genreRepository).findById(id);
        verify(genreRepository).save(existingGenre);
    }

    @Test
    void testUpdateGenreNotFound() {
        Long id = 2L;
        Genre updatedGenre = new Genre();
        updatedGenre.setName("Updated Genre");

        when(genreRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            genreService.update(id, updatedGenre);
        });

        assertEquals("AccountType not found with id " + id, exception.getMessage());
        verify(genreRepository).findById(id);
    }

    @Test
    void testDeleteById() {
        Long id = 3L;

        doNothing().when(genreRepository).deleteById(id);

        genreService.deleteById(id);

        verify(genreRepository).deleteById(id);
    }

    @Test
    void testFindById() {
        Long id = 4L;
        Genre genre = new Genre();
        genre.setName("Strategy");

        when(genreRepository.findById(id)).thenReturn(Optional.of(genre));

        Optional<Genre> result = genreService.findById(id);

        assertTrue(result.isPresent());
        assertEquals("Strategy", result.get().getName());
        verify(genreRepository).findById(id);
    }

    @Test
    void testFindByIdNotFound() {
        Long id = 5L;

        when(genreRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Genre> result = genreService.findById(id);

        assertFalse(result.isPresent());
        verify(genreRepository).findById(id);
    }
}
