package com.example.javagamemvc;

import com.example.javagamemvc.entity.AccountType;
import com.example.javagamemvc.repository.AccountTypeRepository;
import com.example.javagamemvc.service.AccountTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountTypeServiceTest {

    private AccountTypeRepository accountTypeRepository;
    private AccountTypeService accountTypeService;

    @BeforeEach
    void setUp() {
        accountTypeRepository = mock(AccountTypeRepository.class);
        accountTypeService = new AccountTypeService(accountTypeRepository);
    }

    @Test
    @DisplayName("Should return all account types")
    void testFindAll() {
        AccountType type1 = new AccountType();
        type1.setName("Admin");

        AccountType type2 = new AccountType();
        type2.setName("User");

        when(accountTypeRepository.findAll()).thenReturn(Arrays.asList(type1, type2));

        List<AccountType> result = accountTypeService.findAll();

        assertEquals(2, result.size());
        verify(accountTypeRepository).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no account types")
    void testFindAllEmpty() {
        when(accountTypeRepository.findAll()).thenReturn(Collections.emptyList());

        List<AccountType> result = accountTypeService.findAll();

        assertTrue(result.isEmpty());
        verify(accountTypeRepository).findAll();
    }

    @Test
    @DisplayName("Should save an account type")
    void testSave() {
        AccountType accountType = new AccountType();
        accountType.setName("Moderator");

        when(accountTypeRepository.save(accountType)).thenReturn(accountType);

        AccountType saved = accountTypeService.save(accountType);

        assertEquals("Moderator", saved.getName());
        verify(accountTypeRepository).save(accountType);
    }

    @Test
    @DisplayName("Should update existing account type")
    void testUpdate() {
        Long id = 1L;
        AccountType existing = new AccountType();
        existing.setName("Old Name");

        AccountType updated = new AccountType();
        updated.setName("New Name");

        when(accountTypeRepository.findById(id)).thenReturn(Optional.of(existing));
        when(accountTypeRepository.save(existing)).thenReturn(existing);

        AccountType result = accountTypeService.update(id, updated);

        assertEquals("New Name", result.getName());
        verify(accountTypeRepository).findById(id);
        verify(accountTypeRepository).save(existing);
    }

    @Test
    @DisplayName("Should throw exception when updating non-existing account type")
    void testUpdateNotFound() {
        Long id = 2L;
        AccountType updated = new AccountType();
        updated.setName("Name");

        when(accountTypeRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            accountTypeService.update(id, updated);
        });

        assertEquals("AccountType not found with id " + id, ex.getMessage());
        verify(accountTypeRepository).findById(id);
    }

    @Test
    @DisplayName("Should delete account type by ID")
    void testDeleteById() {
        Long id = 3L;

        doNothing().when(accountTypeRepository).deleteById(id);

        accountTypeService.deleteById(id);

        verify(accountTypeRepository).deleteById(id);
    }

    @Test
    @DisplayName("Should find account type by ID")
    void testFindById() {
        Long id = 4L;
        AccountType accountType = new AccountType();
        accountType.setName("Test Type");

        when(accountTypeRepository.findById(id)).thenReturn(Optional.of(accountType));

        Optional<AccountType> result = accountTypeService.findById(id);

        assertTrue(result.isPresent());
        assertEquals("Test Type", result.get().getName());
        verify(accountTypeRepository).findById(id);
    }

    @Test
    @DisplayName("Should return empty when account type not found by ID")
    void testFindByIdNotFound() {
        Long id = 5L;

        when(accountTypeRepository.findById(id)).thenReturn(Optional.empty());

        Optional<AccountType> result = accountTypeService.findById(id);

        assertFalse(result.isPresent());
        verify(accountTypeRepository).findById(id);
    }
}
