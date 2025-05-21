package com.example.javagamemvc.repository;

import com.example.javagamemvc.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
