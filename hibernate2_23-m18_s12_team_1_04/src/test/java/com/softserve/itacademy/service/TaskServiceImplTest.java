package com.softserve.itacademy.service;

import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.repository.TaskRepository;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackages = "com.softserve.itacademy")
public class TaskServiceImplTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ToDoRepository toDoRepository;

    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        taskService = new TaskServiceImpl(taskRepository);
        // Очищення таблиці перед кожним тестом
        taskRepository.deleteAll();
        toDoRepository.deleteAll();
    }

    @Test
    public void testCreateTask() {
        Task task = new Task();
        task.setName("New Task");
        task.setPriority(Priority.HIGH);

        Task createdTask = taskService.create(task);

        assertNotNull(createdTask.getId());
        assertEquals("New Task", createdTask.getName());
    }

    @Test
    public void testReadTaskById() {
        Task task = new Task();
        task.setName("Task for reading");
        task.setPriority(Priority.MEDIUM);
        task = taskRepository.save(task);

        Task foundTask = taskService.readById(task.getId());

        assertNotNull(foundTask);
        assertEquals("Task for reading", foundTask.getName());
    }

    @Test
    public void testUpdateTask() {
        Task task = new Task();
        task.setName("Task for update");
        task.setPriority(Priority.LOW);
        task = taskRepository.save(task);

        task.setName("Updated Task");
        Task updatedTask = taskService.update(task);

        assertEquals("Updated Task", updatedTask.getName());
    }


    @Test
    public void testGetAllTasks() {
        Task task1 = new Task();
        task1.setName("Task 1");
        task1.setPriority(Priority.LOW);

        Task task2 = new Task();
        task2.setName("Task 2");
        task2.setPriority(Priority.MEDIUM);

        taskRepository.save(task1);
        taskRepository.save(task2);

        List<Task> tasks = taskService.getAll();

        assertEquals(2, tasks.size());
    }


}
