package com.crud.task.controller;

import com.crud.task.domain.TaskDto;
import com.crud.task.mapper.TaskMapper;
import com.crud.task.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/task")
public class TaskController {
    @Autowired
    private DbService service;
    @Autowired
    private TaskMapper taskMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/tasks")
    public List<TaskDto> getTasks() {
        return taskMapper.mapToTaskDtoList(service.getAllTasks());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tasks/{taskId}")
    public TaskDto getTask(@PathVariable Long taskId) throws TaskNotFoundException {
        return taskMapper.mapToTaskDto(service.getTask(taskId).orElseThrow(TaskNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/tasks", consumes = APPLICATION_JSON_VALUE)
    public void createTask(@RequestBody TaskDto taskDto) { service.saveTask(taskMapper.mapToTask(taskDto));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/tasks")
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {
        return taskMapper.mapToTaskDto(service.saveTask(taskMapper.mapToTask(taskDto)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/tasks/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        service.deleteTask(taskId);
    }




//    @RequestMapping(method = RequestMethod.GET, value = "getTasks")
//    public List<TaskDto> getTasks() {
//        return taskMapper.mapToTaskDtoList(service.getAllTasks());
//    }
//
//    @RequestMapping(method = RequestMethod.GET, value = "getTask")
//    public TaskDto getTask(@RequestParam Long taskId) throws TaskNotFoundException {
//        return taskMapper.mapToTaskDto(service.getTask(taskId).orElseThrow(TaskNotFoundException::new));
//    }
//
//    @RequestMapping(method = RequestMethod.DELETE, value = "deleteTask")
//    public void deleteTask(@RequestParam Long taskId) {
//        service.deleteTask(taskId);
//    }
//
//    @RequestMapping(method = RequestMethod.PUT, value = "updateTask")
//    public TaskDto updateTask(@RequestBody TaskDto taskDto) {
//        return taskMapper.mapToTaskDto(service.saveTask(taskMapper.mapToTask(taskDto)));
//    }
//
//    @RequestMapping(method = RequestMethod.POST, value = "createTask", consumes = APPLICATION_JSON_VALUE)
//    public void createTask(@RequestBody TaskDto taskDto) { service.saveTask(taskMapper.mapToTask(taskDto));
//    }
}