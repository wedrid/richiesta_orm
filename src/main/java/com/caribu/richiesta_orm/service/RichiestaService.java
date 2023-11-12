package com.caribu.richiesta_orm.service;
import java.util.concurrent.CompletionStage;
import org.hibernate.reactive.stage.Stage.SessionFactory;
import com.caribu.richiesta_orm.data.RichiestaDTOMapper;
import com.caribu.richiesta_orm.data.RichiestaEntityMapper;
import com.caribu.richiesta_orm.model.Richiesta;
import com.caribu.richiesta_orm.model.RichiestaDTO;

import io.vertx.core.Future;

public class RichiestaService {
    private SessionFactory sessionFactory;
    public RichiestaService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Future<RichiestaDTO> addRichiesta(RichiestaDTO richiestaDTO) {
        Richiesta richiestaEntity = new RichiestaEntityMapper().apply(richiestaDTO);
        CompletionStage<Void> result = sessionFactory.withTransaction((s, t) -> s.persist(richiestaEntity));
        RichiestaDTOMapper dtoMapper = new RichiestaDTOMapper(); 
        Future<RichiestaDTO> future = Future.fromCompletionStage(result).map(v -> dtoMapper.apply(richiestaEntity));
        return future;
    }
}
