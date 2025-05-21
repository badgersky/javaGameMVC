package com.example.javagamemvc.controller;

import com.example.javagamemvc.entity.AccountType;
import com.example.javagamemvc.service.AccountTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account-types")
@Tag(name = "Account Types", description = "Endpoints for managing account types")
public class AccountTypeController {

    private final AccountTypeService accountTypeService;

    public AccountTypeController(AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @GetMapping
    @Operation(summary = "Get all account types")
    public ResponseEntity<List<AccountType>> getAllAccountTypes() {
        return ResponseEntity.ok(accountTypeService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get account type by ID")
    public ResponseEntity<AccountType> getAccountTypeById(@PathVariable Long id) {
        return accountTypeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create new account type")
    public ResponseEntity<AccountType> createAccountType(@RequestBody AccountType accountType) {
        AccountType created = accountTypeService.save(accountType);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing account type")
    public ResponseEntity<AccountType> updateAccountType(@PathVariable Long id, @RequestBody AccountType updatedAccountType) {
        AccountType updated = accountTypeService.update(id, updatedAccountType);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete account type by ID")
    public ResponseEntity<Void> deleteAccountType(@PathVariable Long id) {
        accountTypeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
