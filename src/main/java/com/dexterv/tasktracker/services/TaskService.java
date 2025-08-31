package com.dexterv.tasktracker.services;

import com.dexterv.tasktracker.domain.entities.Task;
import com.dexterv.tasktracker.domain.entities.TaskList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {
    List<Task> getAllTasksByTaskListId(UUID taskListId);
    Optional<Task> getTaskByTaskListId(UUID taskListId, UUID taskId);
    Task createTask(UUID taskListId, Task task);
    Task updateTask(UUID taskListId, UUID taskId, Task task);
    void deleteTask(UUID taskListId, UUID taskId);
}
