package com.dexterv.tasktracker.mappers.impl;

import com.dexterv.tasktracker.domain.dto.TaskListDto;
import com.dexterv.tasktracker.domain.entities.Task;
import com.dexterv.tasktracker.domain.entities.TaskList;
import com.dexterv.tasktracker.domain.entities.TaskStatus;
import com.dexterv.tasktracker.mappers.TaskListMapper;
import com.dexterv.tasktracker.mappers.TaskMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskListMapperImpl implements TaskListMapper {

    private final TaskMapper taskMapper;

    public TaskListMapperImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    // Convert DTO -> Entity
    // DTO from frontend → Entity for DB
    @Override
    public TaskList fromDto(TaskListDto taskListDto) {
        return new  TaskList(
                taskListDto.id(),
                taskListDto.title(),
                taskListDto.description(),
                Optional.ofNullable(taskListDto.tasks())
                        .map(tasks -> tasks.stream()
                                .map(taskMapper::fromDto)
                                .toList()
                        ).orElse(null),
                null,
                null
        );
    }

//    Flow:
//    TaskListDto (JSON/API)
//       |
//       v
//    Optional.ofNullable(taskListDto.tasks())
//        |
//        v (tasks not null)
//    tasks.stream()
//        -> take each TaskDto
//        -> call taskMapper.fromDto(taskDto)
//        -> returns a Task entity
//       |
//       v
//    [toList()] -> List<Task>
//       |
//       v
//    TaskList (Entity with tasks, for DB)
//
//
// So your frontend JSON becomes a TaskList entity ready to save in the database.





    // Convert Entity -> DTO
    // Entity from DB → DTO for frontend
    @Override
    public TaskListDto toDto(TaskList taskList) {
        return new TaskListDto(
                taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),
                Optional.ofNullable(taskList.getTasks())
                        .map(List::size).orElse(0),
                calculateTaskListProgress(taskList.getTasks()),
                Optional.ofNullable(taskList.getTasks())
                        .map(tasks ->
                                tasks.stream().map(taskMapper::toDto).toList())
                        .orElse(null),
                taskList.getCreated(),
                taskList.getUpdated()
        );
    }


//    Flow:
//    TaskList (Entity from DB)
//        |
//        v
//    Optional.ofNullable(taskList.getTasks())
//        |
//        v (tasks not null)
//tasks.stream()
//        -> take each Task
//        -> call taskMapper.toDto(task)
//         -> returns TaskDto
//        |
//        v
//   [toList()] -> List<TaskDto>
//               |
//               +--> count size (2)
//               |
//               +--> calculate progress
//    closed=1 / total=2 = 0.5
//            |
//            v
//    TaskListDto {
//        id=123,
//                title="My First List",
//                description="List for demo",
//                taskCount=2,
//                progress=0.5,
//                tasks=[TaskDto("Task A"), TaskDto("Task B")]
//    }

//So your database entity becomes a clean DTO for the API response.

    private Double calculateTaskListProgress(List<Task> tasks) {

        if(null == tasks || tasks.isEmpty()) {
            return 0.0;
        }

        long closedTaskCount = tasks.stream().filter(task -> TaskStatus.CLOSED.equals(task.getStatus())).count();

        return (double) closedTaskCount / tasks.size();
    }
}
