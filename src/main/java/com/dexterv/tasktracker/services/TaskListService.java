package com.dexterv.tasktracker.services;

import com.dexterv.tasktracker.domain.entities.TaskList;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskListService {
    List<TaskList> listTaskLists();
    TaskList createTaskList(TaskList taskList);
    Optional<TaskList> getTaskListById(UUID taskListId);
    TaskList updateTaskList(UUID taskListId, TaskList taskList);
    void deleteTaskListById(UUID taskListId);
}
