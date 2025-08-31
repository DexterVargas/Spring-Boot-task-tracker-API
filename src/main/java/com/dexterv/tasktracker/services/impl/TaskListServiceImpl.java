package com.dexterv.tasktracker.services.impl;

import com.dexterv.tasktracker.controllers.GlobalExceptionHandler;
import com.dexterv.tasktracker.domain.entities.TaskList;
import com.dexterv.tasktracker.repositories.TaskListRepository;
import com.dexterv.tasktracker.services.TaskListService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListServiceImpl implements TaskListService {
    private final TaskListRepository taskListRepository;

    public TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }


    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepository.findAll();
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        if (null != taskList.getId()) {
            throw new IllegalArgumentException("Task list id can't be null!");
        }
        if (null == taskList.getTitle() ||  taskList.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task list title can't be null!");
        }

        LocalDateTime now = LocalDateTime.now();
        return taskListRepository.save(new TaskList(
                null,
                taskList.getTitle(),
                taskList.getDescription(),
                null,
                now,
                now
                )
        );

    }

    @Override
    public Optional<TaskList> getTaskListById(UUID id) {
        return taskListRepository.findById(id);
    }

    LocalDateTime now = LocalDateTime.now();
    @Override
    public TaskList updateTaskList(UUID taskListId, TaskList taskList) {

        if (null == taskList.getId()) {
            throw new IllegalArgumentException("Task list id can't be null!");
        }

        if(!Objects.equals(taskList.getId(), taskListId)) {
            throw new IllegalArgumentException("Task list id does not match! Attempting to change task list!");
        }

//        return taskListRepository.findById(taskListId)
//                .map( existing -> {
//                    existing.setTitle(taskList.getTitle());
//                    existing.setDescription(taskList.getDescription());
//                    existing.setUpdated(now);
//                    return taskListRepository.save(existing);
//                }).orElseThrow( () -> new IllegalArgumentException("Task List not found!"));

        // or

        TaskList existingTaskList = taskListRepository.findById(taskListId).orElseThrow( () ->
                new IllegalArgumentException("Task List not found!"));

        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription(taskList.getDescription());
        existingTaskList.setUpdated(now);
        return taskListRepository.save(existingTaskList);
    }

    @Override
    public void deleteTaskListById(UUID taskListId) {
        taskListRepository.deleteById(taskListId);
    }
}
