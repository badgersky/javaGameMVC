package com.example.javagamemvc;

import com.example.javagamemvc.controller.AccountTypeController;
import com.example.javagamemvc.entity.AccountType;
import com.example.javagamemvc.service.AccountTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountTypeControllerTest {

    private AccountTypeService accountTypeService;
    private AccountTypeController accountTypeController;

    @BeforeEach
    void setUp() {
        accountTypeService = mock(AccountTypeService.class);
        accountTypeController = new AccountTypeController(accountTypeService);
    }

    @Test
    void testGetAllAccountTypes() {
        AccountType at1 = new AccountType();
        at1.setName("ADMIN");
        AccountType at2 = new AccountType();
        at2.setName("USER");

        when(accountTypeService.findAll()).thenReturn(Arrays.asList(at1, at2));

        ResponseEntity<List<AccountType>> response = accountTypeController.getAllAccountTypes();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(accountTypeService).findAll();
    }

    @Test
    void testGetAccountTypeByIdFound() {
        AccountType at = new AccountType();
        at.setName("ADMIN");
        when(accountTypeService.findById(1L)).thenReturn(Optional.of(at));

        ResponseEntity<AccountType> response = accountTypeController.getAccountTypeById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("ADMIN", response.getBody().getName());
    }

    @Test
    void testGetAccountTypeByIdNotFound() {
        when(accountTypeService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<AccountType> response = accountTypeController.getAccountTypeById(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testCreateAccountType() {
        AccountType input = new AccountType();
        input.setName("MODERATOR");

        when(accountTypeService.save(ArgumentMatchers.any(AccountType.class))).thenReturn(input);

        ResponseEntity<AccountType> response = accountTypeController.createAccountType(input);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("MODERATOR", response.getBody().getName());
        verify(accountTypeService).save(ArgumentMatchers.any(AccountType.class));
    }

    @Test
    void testUpdateAccountType() {
        AccountType updated = new AccountType();
        updated.setName("SUPER_ADMIN");

        when(accountTypeService.update(eq(1L), any(AccountType.class))).thenReturn(updated);

        ResponseEntity<AccountType> response = accountTypeController.updateAccountType(1L, updated);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("SUPER_ADMIN", response.getBody().getName());
        verify(accountTypeService).update(eq(1L), any(AccountType.class));
    }

    @Test
    void testDeleteAccountType() {
        doNothing().when(accountTypeService).deleteById(1L);

        ResponseEntity<Void> response = accountTypeController.deleteAccountType(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(accountTypeService).deleteById(1L);
    }
}
