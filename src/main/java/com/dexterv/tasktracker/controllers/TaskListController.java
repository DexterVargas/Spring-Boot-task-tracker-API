package com.dexterv.tasktracker.controllers;

import com.dexterv.tasktracker.domain.dto.TaskListDto;
import com.dexterv.tasktracker.domain.entities.TaskList;
import com.dexterv.tasktracker.mappers.TaskListMapper;
import com.dexterv.tasktracker.services.TaskListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path="/task-lists")
public class TaskListController {

    private final TaskListService taskListService;
    private final TaskListMapper taskListMapper;
    public TaskListController(TaskListService taskListService, TaskListMapper taskListMapper) {
        this.taskListService = taskListService;
        this.taskListMapper = taskListMapper;
    }

    @GetMapping
    public List<TaskListDto> listTaskLists() {
        return taskListService.listTaskLists().stream()
                .map(taskListMapper::toDto)
                .toList();
    }

    //    @GetMapping("/tasks")        // uses value
    //    @GetMapping(path="/tasks")   // uses path
    @GetMapping(path="/{task_list_id}")
    public Optional<TaskListDto> getTaskListsById(@PathVariable("task_list_id") UUID task_list_id) {
        return taskListService.getTaskListById(task_list_id).map(taskListMapper::toDto);
    }

    @PutMapping(path="/{task_list_id}")
    public TaskListDto updateTaskList(
            @PathVariable("task_list_id") UUID taskListId,
            @RequestBody TaskListDto taskListDto) {

        TaskList updatedTaskList = taskListService.updateTaskList(taskListId, taskListMapper.fromDto(taskListDto));

        return  taskListMapper.toDto(updatedTaskList);
    }

    @DeleteMapping(path="/{task_list_id}")
    public ResponseEntity<String> deleteTaskList(@PathVariable("task_list_id") UUID taskListId) {

       taskListService.deleteTaskListById(taskListId);
        return ResponseEntity.noContent().build(); // Returns 204 No Content
    }
    
    @PostMapping
    public TaskListDto createTaskList(@RequestBody TaskListDto taskListDto) {

        TaskList savedTaskList = taskListService.createTaskList(
                taskListMapper.fromDto(taskListDto)
        );

        return taskListMapper.toDto(savedTaskList);

    }
}
