package com.example.javagamemvc.controller;

import com.example.javagamemvc.entity.Studio;
import com.example.javagamemvc.service.StudioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/studios")
@Tag(name = "Studio Controller", description = "Endpoints for managing game studios")
public class StudioController {

    private final StudioService studioService;

    public StudioController(StudioService studioService) {
        this.studioService = studioService;
    }

    @GetMapping
    @Operation(summary = "Get all game studios")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Studio>> getAllStudios() {
        return ResponseEntity.ok(studioService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get studio by ID")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Studio> getStudioById(@PathVariable Long id) {
        return studioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create new game studio")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Studio> createStudio(@RequestBody Studio studio) {
        return ResponseEntity.ok(studioService.save(studio));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing studio by ID")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Studio> updateStudio(@PathVariable Long id, @RequestBody Studio updatedStudio) {
        return ResponseEntity.ok(studioService.update(id, updatedStudio));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete studio by ID")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteStudio(@PathVariable Long id) {
        studioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
