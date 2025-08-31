package com.dexterv.tasktracker.services.impl;

import com.dexterv.tasktracker.domain.entities.Task;
import com.dexterv.tasktracker.domain.entities.TaskList;
import com.dexterv.tasktracker.domain.entities.TaskPriority;
import com.dexterv.tasktracker.domain.entities.TaskStatus;
import com.dexterv.tasktracker.repositories.TaskListRepository;
import com.dexterv.tasktracker.repositories.TaskRepository;
import com.dexterv.tasktracker.services.TaskService;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private TaskListRepository taskListRepository;
    public TaskServiceImpl(TaskRepository taskRepository,  TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public Optional<Task> getTaskByTaskListId(UUID taskListId, UUID taskId) {
        return taskRepository.findByTaskListIdAndId(taskListId, taskId);
    }

    // Get all TASKS under one Task List
    @Override
    public List<Task> getAllTasksByTaskListId(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    @Override
    public Task createTask(UUID taskListId ,Task task) {

        if (null != task.getId()) {
            throw new IllegalArgumentException("Task id can't be null!");
        }
        if (null == task.getTitle() ||  task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task title can't be null!");
        }
        if (null == task.getStatus()) {
            throw new IllegalArgumentException("Task status can't be null!");
        }
        if (null == task.getPriority()) {
            throw new IllegalArgumentException("Task priority can't be null!");
        }

        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("TaskList not found!"));

        task.setTaskList(taskList);
        LocalDateTime now = LocalDateTime.now();
        task.setCreated(now);
        task.setUpdated(now);

        return taskRepository.save(task);

    }

    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {

        System.out.println("Commence updateTask: " + taskId + " " + task);

        Task existingTask = taskRepository.findByTaskListIdAndId(taskListId, taskId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Task not found for given TaskListId and TaskId"));
        System.out.println("Existing Task -> id=" + existingTask.getId() + ", title=" + existingTask.getTitle());

        if (task.getTitle() == null || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task title can't be null or blank!");
        }
        if (task.getStatus() == null) {
            throw new IllegalArgumentException("Task status can't be null!");
        }
        if (task.getPriority() == null) {
            throw new IllegalArgumentException("Task priority can't be null!");
        }

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setStatus(task.getStatus());
        existingTask.setPriority(task.getPriority());
        existingTask.setUpdated(LocalDateTime.now());

        return taskRepository.save(existingTask);

    }

    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        System.out.println("Commence Delete Task : " + taskId );

        Task existingTask = taskRepository.findByTaskListIdAndId(taskListId, taskId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Task not found for given TaskListId and TaskId"));

        taskRepository.delete(existingTask);
    }
}
