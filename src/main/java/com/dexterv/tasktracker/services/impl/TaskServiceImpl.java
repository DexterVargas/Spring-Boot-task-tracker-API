package com.dexterv.tasktracker.services.impl;

import com.dexterv.tasktracker.domain.entities.Task;
import com.dexterv.tasktracker.domain.entities.TaskList;
import com.dexterv.tasktracker.domain.entities.TaskPriority;
import com.dexterv.tasktracker.domain.entities.TaskStatus;
import com.dexterv.tasktracker.repositories.TaskListRepository;
import com.dexterv.tasktracker.repositories.TaskRepository;
import com.dexterv.tasktracker.services.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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

    @Transactional
    @Override
    public Task createTask(UUID taskListId ,Task task) {

        if (null != task.getId()) {
            throw new IllegalArgumentException("Task id already exists!");
        }
        if (null == task.getTitle() ||  task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task title can't be null!");
        }
        TaskPriority taskPriority = Optional.ofNullable(task.getPriority())
                .orElse(TaskPriority.MEDIUM);

        TaskStatus taskStatus = TaskStatus.OPEN;

        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("TaskList not found!"));


        LocalDateTime now = LocalDateTime.now();

//        Task newTask = new Task(
//                null,
//                task.getTitle(),
//                task.getDescription(),
//                task.getDueDate(),
//                taskStatus,
//                taskPriority,
//                taskList,
//                now,
//                now
//        );
//
//        return taskRepository.save(newTask);
//
//        OR

        task.setStatus(taskStatus);
        task.setPriority(taskPriority);
        task.setTaskList(taskList);
        task.setCreated(now);
        task.setUpdated(now);

        return taskRepository.save(task);

    }

    @Transactional
    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {

        System.out.println("updateTask: " + taskId + " " + task);

        if (!Objects.equals(taskId, task.getId())) {
            throw new IllegalArgumentException("Task id does not match task id");
        }
        if (task.getTitle() == null || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task title can't be null or blank!");
        }
        TaskPriority taskPriority = Optional.ofNullable(task.getPriority())
                .orElse(TaskPriority.MEDIUM);

        TaskStatus taskStatus = Optional.ofNullable(task.getStatus())
                .orElse(TaskStatus.OPEN);

        Task existingTask = taskRepository.findByTaskListIdAndId(taskListId, taskId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Task not found for given TaskListId and TaskId"));

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setStatus(taskStatus);
        existingTask.setPriority(taskPriority);
        existingTask.setUpdated(LocalDateTime.now());

        return taskRepository.save(existingTask);

    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        System.out.println("deleteTask : " + taskId );

        taskRepository.deleteByTaskListIdAndId(taskListId, taskId);

    }
}
