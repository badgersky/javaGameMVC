package com.example.javagamemvc;

import com.example.javagamemvc.entity.Studio;
import com.example.javagamemvc.repository.StudioRepository;
import com.example.javagamemvc.service.StudioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudioServiceTest {

    private StudioRepository studioRepository;
    private StudioService studioService;

    @BeforeEach
    void setUp() {
        studioRepository = mock(StudioRepository.class);
        studioService = new StudioService(studioRepository);
    }

    @Test
    @DisplayName("Should return all studios")
    void testFindAll() {
        Studio studio1 = new Studio();
        studio1.setName("Studio One");

        Studio studio2 = new Studio();
        studio2.setName("Studio Two");

        when(studioRepository.findAll()).thenReturn(Arrays.asList(studio1, studio2));

        List<Studio> studios = studioService.findAll();

        assertEquals(2, studios.size());
        verify(studioRepository).findAll();
    }

    @Test
    @DisplayName("Should return empty studio list")
    void testFindAllEmpty() {
        when(studioRepository.findAll()).thenReturn(Collections.emptyList());

        List<Studio> studios = studioService.findAll();

        assertEquals(0, studios.size());
        verify(studioRepository).findAll();
    }

    @Test
    @DisplayName("Should save a new studio")
    void testSaveStudio() {
        Studio studio = new Studio();
        studio.setName("New Studio");

        when(studioRepository.save(studio)).thenReturn(studio);

        Studio savedStudio = studioService.save(studio);

        assertEquals("New Studio", savedStudio.getName());
        verify(studioRepository).save(studio);
    }

    @Test
    @DisplayName("Should update existing studio")
    void testUpdateStudio() {
        Long id = 1L;
        Studio existingStudio = new Studio();
        existingStudio.setName("Old Studio");

        Studio updatedStudio = new Studio();
        updatedStudio.setName("Updated Studio");

        when(studioRepository.findById(id)).thenReturn(Optional.of(existingStudio));
        when(studioRepository.save(existingStudio)).thenReturn(existingStudio);

        Studio result = studioService.update(id, updatedStudio);

        assertEquals("Updated Studio", result.getName());
        verify(studioRepository).findById(id);
        verify(studioRepository).save(existingStudio);
    }

    @Test
    @DisplayName("Should throw exception when updating non-existing studio")
    void testUpdateStudioNotFound() {
        Long id = 2L;
        Studio updatedStudio = new Studio();
        updatedStudio.setName("Updated Studio");

        when(studioRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studioService.update(id, updatedStudio);
        });

        assertEquals("AccountType not found with id " + id, exception.getMessage());
        verify(studioRepository).findById(id);
    }

    @Test
    @DisplayName("Should delete studio by ID")
    void testDeleteById() {
        Long id = 3L;

        doNothing().when(studioRepository).deleteById(id);

        studioService.deleteById(id);

        verify(studioRepository).deleteById(id);
    }

    @Test
    @DisplayName("Should return studio by ID")
    void testFindById() {
        Long id = 4L;
        Studio studio = new Studio();
        studio.setName("Sample Studio");

        when(studioRepository.findById(id)).thenReturn(Optional.of(studio));

        Optional<Studio> result = studioService.findById(id);

        assertTrue(result.isPresent());
        assertEquals("Sample Studio", result.get().getName());
        verify(studioRepository).findById(id);
    }

    @Test
    @DisplayName("Should return empty Optional when studio not found")
    void testFindByIdNotFound() {
        Long id = 5L;

        when(studioRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Studio> result = studioService.findById(id);

        assertFalse(result.isPresent());
        verify(studioRepository).findById(id);
    }
}
