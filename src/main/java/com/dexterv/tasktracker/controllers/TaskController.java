package com.dexterv.tasktracker.controllers;

import com.dexterv.tasktracker.domain.dto.TaskDto;
import com.dexterv.tasktracker.domain.entities.Task;
import com.dexterv.tasktracker.mappers.TaskMapper;
import com.dexterv.tasktracker.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path="/task-lists/{task_list_id}/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;
    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    // Get all tasks
    @GetMapping
    public List<TaskDto> getAllTasks(@PathVariable("task_list_id") UUID taskListId) {
        return taskService.getAllTasksByTaskListId(taskListId)
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    // Add new task
    @PostMapping
    public TaskDto createTask(@PathVariable("task_list_id") UUID taskListId, @RequestBody TaskDto taskDto) {
        Task savedTask = taskService.createTask(taskListId, taskMapper.fromDto(taskDto));

        return taskMapper.toDto(savedTask);
    }

    // GET a task by ID within a specific TaskList
    @GetMapping(path="/{task_id}")
    public Optional<TaskDto> getTask(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID taskId){

        return taskService.getTaskByTaskListId(taskListId, taskId)
                .map(taskMapper::toDto);

    }

    @PutMapping(path="/{task_id}")
    public TaskDto updateTask(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID taskId,
            @RequestBody TaskDto taskDto
    ){
        Task updatedTask = taskService.updateTask(
                taskListId,
                taskId,
                taskMapper.fromDto(taskDto)
        );

        return taskMapper.toDto(updatedTask);
    }

    @DeleteMapping(path="/{task_id}")
    public ResponseEntity<String> deleteTask(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID taskId
    ){
        taskService.deleteTask(taskListId, taskId);
        return ResponseEntity.noContent().build();
    }

}
