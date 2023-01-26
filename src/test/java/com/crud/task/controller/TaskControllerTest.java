package com.crud.task.controller;

import com.crud.task.domain.Task;
import com.crud.task.domain.TaskDto;
import com.crud.task.mapper.TaskMapper;
import com.crud.task.service.DbService;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldFetchTasksLists() throws Exception {
        //given
        Task task = new Task(1L, "test task", "test to do");
        Task task2 = new Task(2L, "test task2", "learn Java");

        TaskDto taskDto = new TaskDto(1L, "test task", "test to do");
        TaskDto taskDto2 = new TaskDto(2L, "test task2", "learn Java");

        List<TaskDto> taskListDto = Arrays.asList(taskDto, taskDto2);

        List<Task> taskList = Arrays.asList(task, task2);

        when(service.getAllTasks()).thenReturn(taskList);
        when(taskMapper.mapToTaskDtoList(taskList)).thenReturn(taskListDto);

        //when & then

        mockMvc.perform(get("/task/tasks").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].title", is("test task")))
                .andExpect(jsonPath("$.[0].content", is("test to do")))
                .andExpect(jsonPath("$.[1].id", is(2)))
                .andExpect(jsonPath("$.[1].title", is("test task2")))
                .andExpect(jsonPath("$.[1].content", is("learn Java"))
                );
        verify(service, times(1)).getAllTasks();
        verify(taskMapper, times(1)).mapToTaskDtoList(taskList);
        verifyNoMoreInteractions(service);
        verifyNoMoreInteractions(taskMapper);
    }

    @Test
    public void shouldFetchEmptyTasksLists() throws Exception {
        //given
        List<TaskDto> taskListDto = new ArrayList<>();

        when(service.getAllTasks()).thenReturn(new ArrayList<>());
        when(taskMapper.mapToTaskDtoList(anyList())).thenReturn(taskListDto);

        //when & then

        mockMvc.perform(get("/task/tasks").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0))
                );
        verify(service, times(1)).getAllTasks();
        verify(taskMapper, times(1)).mapToTaskDtoList(anyList());
        verifyNoMoreInteractions(service);
        verifyNoMoreInteractions(taskMapper);
    }

    @Test
    public void shouldFetchTask() throws Exception {

        //given
        Task task = new Task(
                1L,
                "test task",
                "test to do"
        );

        TaskDto taskDto = new TaskDto(
                1L,
                "test task",
                "test to do"
        );

        when(service.getTask(1L)).thenReturn(Optional.of(task));
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);

        mockMvc.perform(get("/task/tasks/{id}", 1L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("test task")))
                .andExpect(jsonPath("$.content", is("test to do")));
        verify(service, times(1)).getTask(1L);
        verify(taskMapper, times(1)).mapToTaskDto(any(Task.class));
        verifyNoMoreInteractions(service);
        verifyNoMoreInteractions(taskMapper);
    }

    @Test
    public void testGetTaskNonExistent() throws Exception, TaskNotFoundException {

        //given

        //when & then
        try {
            mockMvc.perform(get("/task/tasks/{id}", 99L).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(404))
                    .andExpect(content().string("Id Not Found"))
                    .andDo(print());
        } catch (TaskNotFoundException e) {
            Assert.fail(e.toString());
        } finally {
            verify(service, times(1)).getTask(99L);
            verifyNoMoreInteractions(service);
        }
    }

    @Test
    public void testDeleteTask() throws Exception {

        //given
        Task task = new Task(
                1L,
                "test task",
                "test to do"
        );

        when(service.getTask(task.getId())).thenReturn(Optional.of(task));
        doNothing().when(service).deleteTask(task.getId());

        //when & then
        mockMvc.perform(delete("/task/tasks/{id}", task.getId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNoContent()
                );
        verify(service, times(1)).getTask(task.getId());
        verify(service, times(1)).deleteTask(task.getId());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void testUpdateTask() throws Exception {

        //given
        Task task = new Task(
                1L,
                "test task",
                "test to do"
        );

        TaskDto taskDto = new TaskDto(
                1L,
                "test task",
                "test to do"
        );

        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);
        when(service.saveTask(any(Task.class))).thenReturn(task);
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //when & then
        mockMvc.perform(put("/task/tasks/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(("UTF-8"))
                        .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("test task")))
                .andExpect(jsonPath("$.content", is("test to do"))
                );
        verify(taskMapper, times(1)).mapToTask(any(TaskDto.class));
        verify(taskMapper, times(1)).mapToTaskDto(any(Task.class));
        verify(service, times(1)).saveTask(task);
        verifyNoMoreInteractions(taskMapper);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void shouldCreateTask() throws Exception {

        //given
        Task task = new Task(
                1L,
                "test task",
                "test to do"
        );

        TaskDto taskDto = new TaskDto(
                1L,
                "test task",
                "test to do"
        );

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(service.saveTask(task)).thenReturn(task);

        //when & then
        mockMvc.perform(post("/task/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(("UTF-8"))
                        .content(jsonContent))
                .andExpect(status().is(201))
                .andExpect(header().string("location", containsString("http://localhost:8080/tasks")));

    }
}
