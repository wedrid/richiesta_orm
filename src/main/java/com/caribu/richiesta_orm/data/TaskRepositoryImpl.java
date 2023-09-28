package com.caribu.richiesta_orm.data;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import org.hibernate.reactive.stage.Stage;

import com.caribu.richiesta_orm.model.Task;
import com.caribu.richiesta_orm.model.TaskDTO;
import com.caribu.richiesta_orm.model.TasksList;

import io.vertx.core.Future;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class TaskRepositoryImpl implements TaskRepository{
    private Stage.SessionFactory sessionFactory; //final for sure?

    public TaskRepositoryImpl(Stage.SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Future<TaskDTO> createTask(TaskDTO task) {
   
        TaskEntityMapper entityMapper = new TaskEntityMapper();
        Task entity = entityMapper.apply(task);
        CompletionStage<Void> result = sessionFactory.withTransaction((s,t) -> s.persist(entity));
        TaskDTOMapper dtoMapper = new TaskDTOMapper();
        //we call map as soon as the transaction is completed (the s.persist) so we know that we have already persisted the entity into the database, so the entity already has an id.
        // we can use the result and map it to the dto. 
        Future<TaskDTO> future = Future.fromCompletionStage(result).map(v -> dtoMapper.apply(entity));
        return future;
    }

    @Override
    public Future<TaskDTO> updateTask(TaskDTO task) {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaUpdate<Task> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Task.class);
        Root<Task> root = criteriaUpdate.from(Task.class);
        Predicate predicate = criteriaBuilder.equal(root.get("id"), task.getId());

        criteriaUpdate.where(predicate);

        criteriaUpdate.set("content", task.getContent()); //FIXME: there could be an error here, instead of getContent?
        CompletionStage<Integer> result = sessionFactory.withTransaction((s,t) -> s.createQuery(criteriaUpdate).executeUpdate()); 
        Future<TaskDTO> future = Future.fromCompletionStage(result).map(r -> task);
        return future;
    }

    @Override
    public Future<Void> removeTask(Integer id) {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaDelete<Task> criteriaDelete = criteriaBuilder.createCriteriaDelete(Task.class);
        Root<Task> root = criteriaDelete.from(Task.class);
        Predicate predicate = criteriaBuilder.equal(root.get("id"), id);
        criteriaDelete.where(predicate);
        // DELETE FROM tasks WHERE id = [id]
        // hibernate usa l'api per creare il predicato, in questo caso è più safe ed è anche utile per migrare in futuro da diversi tipi di db
        CompletionStage<Integer> result = sessionFactory.withTransaction((s,t) -> s.createQuery(criteriaDelete).executeUpdate());
        Future<Void> future = Future.fromCompletionStage(result).compose(r -> Future.succeededFuture());
        return future;
    }

    @Override
    public Future<Optional<TaskDTO>> findTaskById(Integer id) {
        //we have an optional now
        //1. create instance of TaskDTOMapper
        TaskDTOMapper dtoMapper = new TaskDTOMapper();
        CompletionStage<Task> result = sessionFactory.withTransaction((s,t) -> s.find(Task.class, id));
        Future<Optional<TaskDTO>> future = Future.fromCompletionStage(result)
            .map(r -> Optional.ofNullable(r))
            .map(r -> r.map(dtoMapper));
        return future;
    }

    @Override
    public Future<TasksList> findTaskByUserId(Integer userId) {
        TaskDTOMapper dtoMapper = new TaskDTOMapper();
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Task> criteriaQuery = criteriaBuilder.createQuery(Task.class);
        Root<Task> root = criteriaQuery.from(Task.class);
        Predicate predicate = criteriaBuilder.equal(root.get("userId"), userId);
        criteriaQuery.where(predicate);
        CompletionStage<List<Task>> result = sessionFactory.withTransaction((s,t) -> s.createQuery(criteriaQuery).getResultList());
        Future<TasksList> future = Future.fromCompletionStage(result)
            .map(list -> list.stream().map(dtoMapper).collect(Collectors.toList()))
            .map(list -> new TasksList(list));
        return future;
    }
    
}
