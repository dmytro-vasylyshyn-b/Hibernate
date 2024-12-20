package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.State;
import com.softserve.itacademy.repository.StateRepository;
import com.softserve.itacademy.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class StateServiceImpl implements StateService {
    private final StateRepository stateRepository;

    @Autowired
    public StateServiceImpl(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public State create(State state) {
        return stateRepository.save(state);
    }

    @Override
    public State readById(long id) {
        return stateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Check the ID that you provided"));
    }

    @Override
    public State update(State state) {
        if (stateRepository.existsById(state.getId())) {
            return stateRepository.save(state);
        } else {
            throw new EntityNotFoundException("State not found");
        }
    }

    @Override
    public void delete(long id) {
        if (stateRepository.existsById(id)) {
            stateRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("State not found");
        }
    }

    @Override
    public List<State> getAll() {
        return stateRepository.findAll();
    }

    @Override
    public List<State> getAllSortedByName() {
        return stateRepository.findAllByOrderByNameAsc();
    }

    @Override
    public State getByName(String name) {
        return stateRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("State not found"));
    }
}

