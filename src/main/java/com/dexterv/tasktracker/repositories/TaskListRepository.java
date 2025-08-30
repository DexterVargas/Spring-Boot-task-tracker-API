package com.dexterv.tasktracker.repositories;

import com.dexterv.tasktracker.domain.entities.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList, UUID> {
    List<TaskList> id(UUID id);
}
