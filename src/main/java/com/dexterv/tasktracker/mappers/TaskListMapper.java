package com.dexterv.tasktracker.mappers;

import com.dexterv.tasktracker.domain.dto.TaskListDto;
import com.dexterv.tasktracker.domain.entities.TaskList;

public interface TaskListMapper {

    // Convert DTO -> Entity
    TaskList fromDto(TaskListDto taskListDto);

    // Convert Entity -> DTO
    TaskListDto toDto(TaskList taskList);
}
