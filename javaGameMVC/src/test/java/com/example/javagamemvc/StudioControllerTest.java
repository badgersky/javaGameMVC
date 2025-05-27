package com.example.javagamemvc;

import com.example.javagamemvc.controller.StudioController;
import com.example.javagamemvc.entity.Studio;
import com.example.javagamemvc.service.StudioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudioControllerTest {

    private StudioService studioService;
    private StudioController studioController;

    @BeforeEach
    void setUp() {
        studioService = mock(StudioService.class);
        studioController = new StudioController(studioService);
    }

    @Test
    void testGetAllStudios() {
        Studio studio1 = new Studio();
        studio1.setName("Studio1");
        Studio studio2 = new Studio();
        studio2.setName("Studio2");

        when(studioService.findAll()).thenReturn(Arrays.asList(studio1, studio2));

        ResponseEntity<List<Studio>> response = studioController.getAllStudios();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(studioService).findAll();
    }

    @Test
    void testGetStudioByIdFound() {
        Studio studio = new Studio();
        studio.setName("TestStudio");
        when(studioService.findById(1L)).thenReturn(Optional.of(studio));

        ResponseEntity<Studio> response = studioController.getStudioById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("TestStudio", response.getBody().getName());
    }

    @Test
    void testGetStudioByIdNotFound() {
        when(studioService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Studio> response = studioController.getStudioById(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testCreateStudio() {
        Studio inputStudio = new Studio();
        inputStudio.setName("NewStudio");

        when(studioService.save(ArgumentMatchers.any(Studio.class))).thenReturn(inputStudio);

        ResponseEntity<Studio> response = studioController.createStudio(inputStudio);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("NewStudio", response.getBody().getName());
        verify(studioService).save(ArgumentMatchers.any(Studio.class));
    }

    @Test
    void testUpdateStudio() {
        Studio updatedStudio = new Studio();
        updatedStudio.setName("UpdatedStudio");

        when(studioService.update(eq(1L), any(Studio.class))).thenReturn(updatedStudio);

        ResponseEntity<Studio> response = studioController.updateStudio(1L, updatedStudio);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("UpdatedStudio", response.getBody().getName());
        verify(studioService).update(eq(1L), any(Studio.class));
    }

    @Test
    void testDeleteStudio() {
        doNothing().when(studioService).deleteById(1L);

        ResponseEntity<Void> response = studioController.deleteStudio(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(studioService).deleteById(1L);
    }
}
