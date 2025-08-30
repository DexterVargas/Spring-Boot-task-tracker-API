package com.dexterv.tasktracker.mappers.impl;

import com.dexterv.tasktracker.domain.dto.TaskDto;
import com.dexterv.tasktracker.domain.entities.Task;
import com.dexterv.tasktracker.mappers.TaskMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapperImpl  implements TaskMapper {

    // Convert DTO -> Entity
    // DTO from frontend → Entity for DB
    @Override
    public Task fromDto(TaskDto dto) {
        return new Task(
                dto.id(),
                dto.title(),
                dto.description(),
                dto.dueDate(),
                dto.status(),
                dto.priority(),
                null,
                null,
                null
        );
    }

    // Convert Entity -> DTO
    // Entity from DB → DTO for frontend
    @Override
    public TaskDto toDto(Task dto) {
        return new TaskDto(
                dto.getId(),
                dto.getTitle(),
                dto.getDescription(),
                dto.getDueDate(),
                dto.getPriority(),
                dto.getStatus()
        );
    }
}
