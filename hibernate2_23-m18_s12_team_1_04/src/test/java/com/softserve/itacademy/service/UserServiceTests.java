package com.softserve.itacademy.service;

import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.RoleRepository;
import com.softserve.itacademy.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private User user;
    private Role role = new Role();

    @BeforeEach
    public void setUp(){
        role = new Role();
        role.setName("Test");
        role = roleRepository.save(role);

        user = new User();
        user.setFirstName("Vadym");
        user.setLastName("Honcharuk");
        user.setEmail("email@gmail.com");
        user.setPassword("qwerty123QWERTY@Create");
        user.setRole(role);
        user.setMyTodos(new ArrayList<>());
    }

    @Test
    public void createValidUserTest() {
        userService.create(user);

        User savedUser = userRepository.findByEmail("email@gmail.com");

        assertNotNull(savedUser);
        assertEquals("Vadym", savedUser.getFirstName());
        assertEquals("Honcharuk", savedUser.getLastName());
        assertEquals("email@gmail.com", savedUser.getEmail());
        assertEquals("Test", savedUser.getRole().getName());
    }

    @Test
    public void createUserAlreadyExistsTest() {
        userService.create(user);

        // Attempt to create the same user again
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.create(user));
        assertEquals("This user already exists or user is null", exception.getMessage());
    }

    @Test
    public void readUserByIdTest() {
        User createdUser = userService.create(user);
        User foundUser = userService.readById(createdUser.getId());

        assertNotNull(foundUser);
        assertEquals(createdUser.getId(), foundUser.getId());
        assertEquals("Vadym", foundUser.getFirstName());
        assertEquals("Honcharuk", foundUser.getLastName());
    }

    @Test
    public void readUserByIdNotFoundTest() {
        long invalidId = 999L;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.readById(invalidId));
        assertEquals("This user does not exist", exception.getMessage());
    }

//    @Test
//    public void updateUserTest() {
//        role = new Role();
//        role.setName("Test222");
//        role = roleRepository.save(role);
//
//        user = new User();
//        user.setFirstName("Vadym");
//        user.setLastName("Honcharuk");
//        user.setEmail("email@gmail.com");
//        user.setPassword("qwerty123QWERTY@Create");
//        user.setRole(role);
//        user.setMyTodos(new ArrayList<>());
//
//        userRepository.save(user);
//
//        User createdUser = userService.create(user);
//
//        createdUser.setFirstName("UpdatedName");
//        userService.update(createdUser);
//
//        User updatedUser = userRepository.findByEmail("email@gmail.com");
//
//        assertNotNull(updatedUser);
//        assertEquals("UpdatedName", updatedUser.getFirstName());
//    }

    @Test
    @Transactional
    public void deleteUserTest() {
        User createdUser = userService.create(user);

        userService.delete(createdUser.getId());

        assertFalse(userRepository.existsById(createdUser.getId()));
    }

    @Test
    public void deleteUserNotFoundTest() {
        long invalidId = 999L;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.delete(invalidId));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void getAllUsersTest() {
        userService.create(user);
        User anotherUser = new User();
        anotherUser.setFirstName("Anna");
        anotherUser.setLastName("Maria");
        anotherUser.setEmail("another@gmail.com");
        anotherUser.setPassword("Password@123");
        anotherUser.setRole(role);
        userService.create(anotherUser);

        List<User> users = userService.getAll();
        assertTrue(users.size() >= 2, "There should be at least 2 users in the USERS table");
    }
}
