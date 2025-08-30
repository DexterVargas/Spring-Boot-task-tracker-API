package com.dexterv.tasktracker.domain.dto;

import com.dexterv.tasktracker.domain.entities.TaskPriority;
import com.dexterv.tasktracker.domain.entities.TaskStatus;

import java.time.LocalDate;
import java.util.UUID;

public record TaskDto(
        UUID id,
        String title,
        String description,
        LocalDate dueDate,
        TaskPriority priority,
        TaskStatus status
) {
}
