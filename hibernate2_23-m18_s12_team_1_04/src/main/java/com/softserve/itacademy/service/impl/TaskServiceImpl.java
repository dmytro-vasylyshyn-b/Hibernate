package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.repository.TaskRepository;
import com.softserve.itacademy.service.TaskService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task create(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task readById(long id) {
        return taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Check the ID that you provided"));
    }

    @Override
    public Task update(Task task) {
        if (taskRepository.existsById(task.getId())) {
            return taskRepository.save(task);
        } else {
            throw new EntityNotFoundException("Task not found");
        }
    }

    @Override
    public void delete(long id) {
        try {
            taskRepository.deleteById(id);
        } catch (EmptyResultDataAccessException | IllegalArgumentException ex) {
            if (ex instanceof EmptyResultDataAccessException) {
                throw new EntityNotFoundException("ID " + id + " not found");
            } else if (ex instanceof IllegalArgumentException) {
                throw new IllegalArgumentException("ID cannot be null", ex);
            }
        }
    }

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> getByTodoId(long todoId) {
        return taskRepository.findByToDoId(todoId);
    }
}
