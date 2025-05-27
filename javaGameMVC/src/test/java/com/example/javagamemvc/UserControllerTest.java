package com.example.javagamemvc;

import com.example.javagamemvc.controller.UserController;
import com.example.javagamemvc.entity.Users;
import com.example.javagamemvc.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private UserController userController;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userController = new UserController(userService, passwordEncoder);
    }

    @Test
    void testGetAllUsers() {
        Users user1 = new Users();
        user1.setUsername("user1");
        Users user2 = new Users();
        user2.setUsername("user2");

        when(userService.findAll()).thenReturn(Arrays.asList(user1, user2));

        ResponseEntity<List<Users>> response = userController.getAllUsers();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(userService).findAll();
    }

    @Test
    void testGetUserByIdFound() {
        Users user = new Users();
        user.setUsername("user");
        when(userService.findById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<Users> response = userController.getUserById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("user", response.getBody().getUsername());
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Users> response = userController.getUserById(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testCreateUser() {
        Users inputUser = new Users();
        inputUser.setPassword("rawPassword");
        Users savedUser = new Users();
        savedUser.setUsername("user");

        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");
        when(userService.save(ArgumentMatchers.any(Users.class))).thenReturn(savedUser);

        ResponseEntity<Users> response = userController.createUser(inputUser);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(savedUser, response.getBody());
        verify(passwordEncoder).encode("rawPassword");
        verify(userService).save(ArgumentMatchers.any(Users.class));
    }

    @Test
    void testUpdateUser() {
        Users updatedUser = new Users();
        updatedUser.setPassword("newRawPassword");
        Users savedUser = new Users();
        savedUser.setUsername("updatedUser");

        when(passwordEncoder.encode("newRawPassword")).thenReturn("encodedNewPassword");
        when(userService.update(eq(1L), any(Users.class))).thenReturn(savedUser);

        ResponseEntity<Users> response = userController.updateUser(1L, updatedUser);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(savedUser, response.getBody());
        verify(passwordEncoder).encode("newRawPassword");
        verify(userService).update(eq(1L), any(Users.class));
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userService).deleteById(1L);

        ResponseEntity<Void> response = userController.deleteUser(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(userService).deleteById(1L);
    }

    @Test
    void testLikeGame() {
        // Mock Security Context
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("username");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        try (MockedStatic<SecurityContextHolder> mocked = Mockito.mockStatic(SecurityContextHolder.class)) {
            mocked.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            Users user = new Users();
            user.setId(1L);
            when(userService.findByUsername("username")).thenReturn(Optional.of(user));
            when(userService.likeGame(1L, 10L)).thenReturn(user);

            ResponseEntity<Users> response = userController.likeGame(10L);

            assertEquals(200, response.getStatusCodeValue());
            assertEquals(user, response.getBody());
            verify(userService).findByUsername("username");
            verify(userService).likeGame(1L, 10L);
        }
    }

    @Test
    void testUnlikeGame() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("username");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        try (MockedStatic<SecurityContextHolder> mocked = Mockito.mockStatic(SecurityContextHolder.class)) {
            mocked.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            Users user = new Users();
            user.setId(1L);
            when(userService.findByUsername("username")).thenReturn(Optional.of(user));
            when(userService.unlikeGame(1L, 20L)).thenReturn(user);

            ResponseEntity<Users> response = userController.unlikeGame(20L);

            assertEquals(200, response.getStatusCodeValue());
            assertEquals(user, response.getBody());
            verify(userService).findByUsername("username");
            verify(userService).unlikeGame(1L, 20L);
        }
    }
}
