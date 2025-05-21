package com.example.javagamemvc.service;

import com.example.javagamemvc.entity.Studio;
import com.example.javagamemvc.repository.StudioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudioService {
    private final StudioRepository studioRepository;

    public StudioService(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }

    public List<Studio> findAll() {
        return studioRepository.findAll();
    }

    public Optional<Studio> findById(Long id) {
        return studioRepository.findById(id);
    }

    public Studio save(Studio studio) {
        return studioRepository.save(studio);
    }

    public Studio update(Long id, Studio updatedStudio) {
        Studio studio = studioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AccountType not found with id " + id));
        studio.setName(updatedStudio.getName());
        return studioRepository.save(studio);
    }

    public void deleteById(Long id) {
        studioRepository.deleteById(id);
    }
}
