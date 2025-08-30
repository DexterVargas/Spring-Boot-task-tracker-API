package com.dexterv.tasktracker.controllers;

import com.dexterv.tasktracker.domain.dto.TaskListDto;
import com.dexterv.tasktracker.domain.entities.TaskList;
import com.dexterv.tasktracker.mappers.TaskListMapper;
import com.dexterv.tasktracker.services.TaskListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
//
    @PostMapping
    public TaskListDto createTaskList(@RequestBody TaskListDto taskListDto) {
//        return taskListService.addTaskList(taskList);
        TaskList taskList = taskListMapper.fromDto(taskListDto);
        System.out.println(taskList.toString());
        TaskList savedTaskList = taskListService.createTaskList(taskList);
        System.out.println(savedTaskList.toString());
        return taskListMapper.toDto(savedTaskList);

    }
}
