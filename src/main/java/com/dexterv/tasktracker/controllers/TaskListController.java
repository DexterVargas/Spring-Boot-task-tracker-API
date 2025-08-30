package com.dexterv.tasktracker.controllers;

import com.dexterv.tasktracker.domain.dto.TaskListDto;
import com.dexterv.tasktracker.domain.entities.TaskList;
import com.dexterv.tasktracker.mappers.TaskListMapper;
import com.dexterv.tasktracker.services.TaskListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path="/task-lists")
public class TaskListController {

    private TaskListService taskListService;
    private TaskListMapper taskListMapper;
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

    @GetMapping(path="/{task_list_id}")
    public Optional<TaskListDto> getTaskListsById(@PathVariable("task_list_id") UUID task_list_id) {
        return taskListService.getTaskListById(task_list_id).map(taskListMapper::toDto);
    }
    
    @PutMapping("/{task_list_id}")
    public TaskList updateTaskList(@PathVariable("task_list_id") UUID task_list_id, @RequestBody TaskListDto taskListDto) {
        return taskListService.updateTaskList(task_list_id, taskListMapper.fromDto(taskListDto));
    }
    
    
    @PostMapping
    public TaskListDto createTaskList(@RequestBody TaskListDto taskListDto) {

        TaskList savedTaskList = taskListService.createTaskList(
                taskListMapper.fromDto(taskListDto)
        );

        return taskListMapper.toDto(savedTaskList);

    }
}
