package com.softserve.itacademy.service.impl;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.service.ToDoService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ToDoServiceImpl implements ToDoService {
    private ToDoRepository doRepository;

    @Override
    public ToDo create(ToDo todo) {
        return doRepository.save(todo);
    }

    @Override
    public ToDo readById(long id) {
        return doRepository.findById(id).orElseThrow(()->new IllegalArgumentException("check the id that you give"));
    }

    @Override
    public ToDo update(ToDo todo) {
        if(doRepository.existsById(todo.getId())) {
            return doRepository.save(todo);
        }
        else throw new EntityNotFoundException("not found");
    }

    @Override
    public void delete(long id) {
       try{
            doRepository.deleteById(id);
       }catch (EmptyResultDataAccessException | IllegalArgumentException ex){
           if (ex instanceof EmptyResultDataAccessException) {
               throw new EntityNotFoundException("id "+id+"not found");
           }else if(ex instanceof IllegalArgumentException){
               throw new IllegalArgumentException("cant be null", ex);
           }
        }
    }

    @Override
    public List<ToDo> getAll()  {
        return doRepository.findAll();
    }

    @Override
    public List<ToDo> getByUserId(long userId) {
        return doRepository.findAllByOwner_Id(userId);
    }
}
