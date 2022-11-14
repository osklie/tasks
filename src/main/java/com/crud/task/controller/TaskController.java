package com.crud.task.controller;

import com.crud.task.domain.Task;
import com.crud.task.domain.TaskDto;
import com.crud.task.mapper.TaskMapper;
import com.crud.task.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final DbService service;
    private final TaskMapper taskMapper;

    @GetMapping
    public List<TaskDto> getTasks() {
        List<Task> tasks = service.getAllTasks();
        return taskMapper.mapToTaskDtoList(tasks);
    }

//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    public void createTask(@RequestBody TaskDto taskDto) {
//        Task task = taskMapper.mapToTask(taskDto);
//        service.saveTask(task);
//    }
//    @RequestMapping(method = RequestMethod.DELETE, value = "deleteTask")
//    public void deleteTask(@RequestParam Long taskId) {
//        service.deleteTask(taskId);
//    }
}