package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
public class RoleRepositoryTests {

    @Autowired
    RoleRepository roleRepository;

    @Test
    public void createRole() {
        Role role = new Role();
        role.setName("NEW");
        role = roleRepository.save(role);
        assertEquals(10, role.getId());
    }

    @Test
    public void getRole() {
        Role role = new Role();
        role.setName("NEW");
        role = roleRepository.save(role);
        assertEquals(role, roleRepository.getOne(1L));
    }

    @Test
    public void deleteRole() {
        Role role = new Role();
        role.setName("NEW");
        role = roleRepository.save(role);
        roleRepository.delete(role);
        assertEquals(0, roleRepository.findAll().size());
    }

    @Test
    public void updateRole() {
        Role role = new Role();
        role.setName("NEW");
        role = roleRepository.save(role);
        role.setName("UPDATED");
        role = roleRepository.save(role);
        assertEquals(10, role.getId());
    }

    @Test
    public void getAllRoles() {
        Role role = new Role();
        role.setName("NEW");
        role = roleRepository.save(role);
        roleRepository.delete(role);
        assertEquals(0, roleRepository.findAll().size());
    }

    @Test
    public void getRoleById() {
        Role role = new Role();
        role.setName("NEW");
        role = roleRepository.save(role);
        assertEquals(role,roleRepository.findById(1L));
    }

}

