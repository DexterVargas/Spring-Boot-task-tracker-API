package com.dexterv.tasktracker.domain.dto;

import com.dexterv.tasktracker.domain.entities.TaskPriority;
import com.dexterv.tasktracker.domain.entities.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskPriority priority,
        TaskStatus status
) {
}
