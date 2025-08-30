package com.dexterv.tasktracker.services.impl;

import com.dexterv.tasktracker.domain.entities.TaskList;
import com.dexterv.tasktracker.repositories.TaskListRepository;
import com.dexterv.tasktracker.services.TaskListService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
}
