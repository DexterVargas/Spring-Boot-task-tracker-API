package com.dexterv.tasktracker.mappers;

import com.dexterv.tasktracker.domain.dto.TaskDto;
import com.dexterv.tasktracker.domain.entities.Task;

public interface TaskMapper {

    // Convert DTO -> Entity
    Task fromDto(TaskDto dto);

    // Convert Entity -> DTO
    TaskDto toDto(Task dto);
}
