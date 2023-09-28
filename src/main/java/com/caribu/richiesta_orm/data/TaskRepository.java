package com.caribu.richiesta_orm.data;

import java.util.Optional;

import com.caribu.richiesta_orm.model.TaskDTO;
import com.caribu.richiesta_orm.model.TasksList;

import io.vertx.core.Future;

public interface TaskRepository {
    Future<TaskDTO> createTask(TaskDTO task);
    Future<TaskDTO> updateTask(TaskDTO task);
    Future<Void> removeTask(Integer id);
    Future<Optional<TaskDTO>> findTaskById(Integer id);
    Future<TasksList> findTaskByUserId(Integer userId);
}
