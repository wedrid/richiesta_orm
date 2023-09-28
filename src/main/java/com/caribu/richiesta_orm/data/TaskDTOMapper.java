package com.caribu.richiesta_orm.data;

import java.util.function.Function;

import com.caribu.richiesta_orm.model.Task;
import com.caribu.richiesta_orm.model.TaskDTO;

public class TaskDTOMapper implements Function<Task, TaskDTO> {

    @Override
    public TaskDTO apply(Task task) {
        return new TaskDTO(task.getId(), task.getUserId(), task.getContent(), task.isCompleted(), task.getCreatedAt());
    }
}
