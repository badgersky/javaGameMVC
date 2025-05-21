package com.example.javagamemvc.service;

import com.example.javagamemvc.entity.AccountType;
import com.example.javagamemvc.repository.AccountTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountTypeService {
    private final AccountTypeRepository accountTypeRepository;

    public AccountTypeService(AccountTypeRepository accountTypeRepository) {
        this.accountTypeRepository = accountTypeRepository;
    }

    public List<AccountType> findAll() {
        return accountTypeRepository.findAll();
    }

    public Optional<AccountType> findById(Long id) {
        return accountTypeRepository.findById(id);
    }

    public AccountType save(AccountType accountType) {
        return accountTypeRepository.save(accountType);
    }

    public AccountType update(Long id, AccountType updatedAccountType) {
        AccountType accountType = accountTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AccountType not found with id " + id));
        accountType.setName(updatedAccountType.getName());
        return accountTypeRepository.save(accountType);
    }

    public void deleteById(Long id) {
        accountTypeRepository.deleteById(id);
    }
}

