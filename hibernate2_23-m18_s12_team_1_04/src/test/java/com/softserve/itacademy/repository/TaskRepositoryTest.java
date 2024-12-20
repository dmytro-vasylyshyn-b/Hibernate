package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackages = "com.softserve.itacademy")
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    public void setUp() {
        // Очищення таблиці перед кожним тестом
        taskRepository.deleteAll();
    }

    @Test
    public void testSaveTask() {
        Task task = new Task();
        task.setName("Test Task");

        Task savedTask = taskRepository.save(task);

        assertNotNull(savedTask.getId());
        assertEquals("Test Task", savedTask.getName());
    }

    @Test
    public void testFindById() {
        Task task = new Task();
        task.setName("Find Task");

        Task savedTask = taskRepository.save(task);
        Optional<Task> foundTask = taskRepository.findById(savedTask.getId());

        assertTrue(foundTask.isPresent());
        assertEquals("Find Task", foundTask.get().getName());
    }

    @Test
    public void testFindAll() {
        Task task1 = new Task();
        task1.setName("Task 1");

        Task task2 = new Task();
        task2.setName("Task 2");

        taskRepository.save(task1);
        taskRepository.save(task2);

        List<Task> tasks = taskRepository.findAll();

        assertEquals(2, tasks.size());
    }

    @Test
    public void testDeleteTask() {
        Task task = new Task();
        task.setName("Task to delete");

        Task savedTask = taskRepository.save(task);
        taskRepository.deleteById(savedTask.getId());

        Optional<Task> deletedTask = taskRepository.findById(savedTask.getId());
        assertFalse(deletedTask.isPresent());
    }

    @Test
    public void testUpdateTask() {
        Task task = new Task();
        task.setName("Initial Name");

        Task savedTask = taskRepository.save(task);

        savedTask.setName("Updated Name");
        Task updatedTask = taskRepository.save(savedTask);

        assertEquals("Updated Name", updatedTask.getName());
    }
}
