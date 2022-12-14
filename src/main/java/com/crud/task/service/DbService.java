package com.crud.task.service;

import com.crud.task.domain.Task;
import com.crud.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DbService {
    @Autowired
    private TaskRepository repository;

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task saveTask(final Task task) {
        return repository.save(task);
    }

    public Optional<Task> getTask(final Long id) {
        return repository.findById(id);
    }
    public void deleteTask(final Long id) {
        repository.deleteById(id);
    }

}