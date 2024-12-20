package com.softserve.itacademy.service;

import com.softserve.itacademy.model.State;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.repository.StateRepository;
import com.softserve.itacademy.repository.TaskRepository;
import com.softserve.itacademy.service.impl.StateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackages = "com.softserve.itacademy")
public class StateServiceTests {

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private TaskRepository taskRepository; // додайте цей репозиторій, якщо ще не додали

    private StateService stateService;

    @BeforeEach
    public void setUp() {
        stateService = new StateServiceImpl(stateRepository);
        taskRepository.deleteAll(); // очищення завдань
        stateRepository.deleteAll();
    }

    @Test
    public void testCreateState() {
        State state = new State();
        state.setName("New State");

        State createdState = stateService.create(state);

        assertNotNull(createdState.getId());
        assertEquals("New State", createdState.getName());
    }

    @Test
    public void testReadStateById() {
        State state = new State();
        state.setName("State for reading");
        State savedState = stateRepository.save(state);

        State foundState = stateService.readById(savedState.getId());

        assertNotNull(foundState);
        assertEquals("State for reading", foundState.getName());
    }

    @Test
    public void testUpdateState() {
        State state = new State();
        state.setName("State for update");
        State savedState = stateRepository.save(state);

        savedState.setName("Updated State");
        State updatedState = stateService.update(savedState);

        assertEquals("Updated State", updatedState.getName());
    }







    @Test
    public void testGetAllStates() {
        State state1 = new State();
        state1.setName("State 1");

        State state2 = new State();
        state2.setName("State 2");

        stateRepository.save(state1);
        stateRepository.save(state2);

        List<State> states = stateService.getAll();

        assertEquals(2, states.size());
    }


    @Test
    public void testGetByName() {
        State state = new State();
        state.setName("New State");
        stateRepository.save(state);

        State foundState = stateService.getByName("New State");

        assertNotNull(foundState);
        assertEquals("New State", foundState.getName());
    }
}

