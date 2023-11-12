package com.caribu.richiesta_orm.service;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.hibernate.reactive.stage.Stage.SessionFactory;

import com.caribu.richiesta_orm.data.TrattaDTOMapper;
import com.caribu.richiesta_orm.data.TrattaEntityMapper;
import com.caribu.richiesta_orm.model.Tratta;
import com.caribu.richiesta_orm.model.TrattaDTO;

import io.vertx.core.Future;
import io.vertx.core.net.impl.pool.Task;

public class TrattaService {
    private final SessionFactory sessionFactory;
    
    public TrattaService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Future<TrattaDTO> addTratta(TrattaDTO trattaDTO) {
        Tratta trattaEntity = new TrattaEntityMapper().apply(trattaDTO);
        CompletionStage<Void> result = sessionFactory.withTransaction((s, t) -> s.persist(trattaEntity));
        TrattaDTOMapper dtoMapper = new TrattaDTOMapper();
        Future<TrattaDTO> future = Future.fromCompletionStage(result).map(v -> dtoMapper.apply(trattaEntity));
        return future;
    }

    public Future<Optional<Tratta>> getTrattaById(Integer id){
        TrattaDTOMapper dtoMapper = new TrattaDTOMapper();
        CompletionStage<Tratta> result = sessionFactory.withTransaction((s,t) -> s.find(Tratta.class, id));
        Future<Optional<Tratta>> future = Future.fromCompletionStage(result)
            .map(r -> Optional.ofNullable(r));

        future.onComplete(ar -> {
            if (ar.succeeded()) {
                ar.result().ifPresent(tratta -> {
                    // TODO: si ricomincia da qua
                    System.out.println("##**Retrieved Tratta with id: " + tratta.getId());
                });
            } else {
                System.out.println("Retrieval failed: " + ar.cause().getMessage());
            }
        });

        return future;
        ///
        /* 
        TaskDTOMapper dtoMapper = new TaskDTOMapper();
        CompletionStage<Task> result = sessionFactory().withTransaction((s,t) -> s.find(Task.class, id));
        Future<Optional<TaskDTO>> future = Future.fromCompletionStage(result)
        .map(r -> Optional.ofNullable(r))
        .map(r -> r.map(dtoMapper));
        return future;
        */
    
    }
    
}