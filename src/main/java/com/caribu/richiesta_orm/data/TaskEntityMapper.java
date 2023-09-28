package com.caribu.richiesta_orm.data;

import java.util.function.Function;

import com.caribu.richiesta_orm.model.Task;
import com.caribu.richiesta_orm.model.TaskDTO;

class TaskEntityMapper implements Function<TaskDTO, Task>{

    @Override
    public Task apply(TaskDTO taskDTO) {
        Task task = new Task();
        task.setId(taskDTO.getId());
        task.setUserId(taskDTO.getUserId());
        task.setContent(taskDTO.getContent());
        task.setCompleted(taskDTO.isCompleted());
        task.setCreatedAt(taskDTO.getCreated());
        return task;
    }
    
}
