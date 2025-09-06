package com.dexterv.tasktracker.domain.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TaskListDto(
        UUID id,
        String title,
        String description,
        Integer count,
        Double progress,
        List<TaskDto> tasks,
        LocalDateTime created,
        LocalDateTime updated
) {
}
