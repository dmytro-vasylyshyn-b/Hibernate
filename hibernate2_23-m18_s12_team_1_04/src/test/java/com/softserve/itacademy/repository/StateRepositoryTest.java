package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.State;
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
public class StateRepositoryTest {

    @Autowired
    private StateRepository stateRepository;

    @BeforeEach
    public void setUp() {
        // Очищення таблиці перед кожним тестом
        stateRepository.deleteAll();
    }


    @Test
    public void testSaveState() {
        State state = new State();
        state.setName("Test State");

        State savedState = stateRepository.save(state);

        assertNotNull(savedState.getId());
        assertEquals("Test State", savedState.getName());
    }

    @Test
    public void testFindById() {
        State state = new State();
        state.setName("Find State");

        State savedState = stateRepository.save(state);
        Optional<State> foundState = stateRepository.findById(savedState.getId());

        assertTrue(foundState.isPresent());
        assertEquals("Find State", foundState.get().getName());
    }

    @Test
    public void testFindAll() {
        State state1 = new State();
        state1.setName("State 1");

        State state2 = new State();
        state2.setName("State 2");

        stateRepository.save(state1);
        stateRepository.save(state2);

        List<State> states = stateRepository.findAll();

        assertEquals(2, states.size());
    }

    @Test
    public void testDeleteState() {
        State state = new State();
        state.setName("State to delete");

        State savedState = stateRepository.save(state);
        stateRepository.deleteById(savedState.getId());

        Optional<State> deletedState = stateRepository.findById(savedState.getId());
        assertFalse(deletedState.isPresent());
    }

    @Test
    public void testUpdateState() {
        State state = new State();
        state.setName("Initial Name");

        State savedState = stateRepository.save(state);

        savedState.setName("Updated Name");
        State updatedState = stateRepository.save(savedState);

        assertEquals("Updated Name", updatedState.getName());
    }
}
