package com.caribu.richiesta_orm.service;

import java.util.concurrent.CompletionStage;

import org.hibernate.reactive.stage.Stage.SessionFactory;

import com.caribu.richiesta_orm.data.TrattaDTOMapper;
import com.caribu.richiesta_orm.data.TrattaEntityMapper;
import com.caribu.richiesta_orm.model.Tratta;
import com.caribu.richiesta_orm.model.TrattaDTO;

import io.vertx.core.Future;

public class TrattaService {
    private final SessionFactory sessionFactory;
    
    public TrattaService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Future<TrattaDTO> addTratta(TrattaDTO trattaDTO) {
        System.out.println("Adding tratta 1");
        Tratta trattaEntity = new TrattaEntityMapper().apply(trattaDTO);
        System.out.println("Adding tratta 2");
        CompletionStage<Void> result = sessionFactory.withTransaction((s, t) -> s.persist(trattaEntity));
        System.out.println("Adding tratta 3");
        TrattaDTOMapper dtoMapper = new TrattaDTOMapper();
        System.out.println("Adding tratta 4");
        Future<TrattaDTO> future = Future.fromCompletionStage(result).map(v -> dtoMapper.apply(trattaEntity));
        System.out.println("Adding tratta 5");
        return future;
    }
    
}
