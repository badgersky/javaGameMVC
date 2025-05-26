package com.example.javagamemvc.config;

import com.example.javagamemvc.entity.AccountType;
import com.example.javagamemvc.entity.Users;
import com.example.javagamemvc.repository.AccountTypeRepository;
import com.example.javagamemvc.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository,
                      AccountTypeRepository accountTypeRepository,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.accountTypeRepository = accountTypeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (accountTypeRepository.findByName("ADMIN").isEmpty()) {
            AccountType adminRole = new AccountType();
            adminRole.setName("ADMIN");
            accountTypeRepository.save(adminRole);
        }

        if (accountTypeRepository.findByName("USER").isEmpty()) {
            AccountType userRole = new AccountType();
            userRole.setName("USER");
            accountTypeRepository.save(userRole);
        }

        if (userRepository.findByUsername("admin").isEmpty()) {
            AccountType adminRole = accountTypeRepository.findByName("ADMIN").get();

            Users admin = new Users();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("aa"));
            admin.setAccount_type(adminRole);

            userRepository.save(admin);
        }
    }
}
