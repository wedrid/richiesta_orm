package com.caribu.richiesta_orm.data;

import java.util.Optional;

import com.caribu.richiesta_orm.model.RichiestaDTO;
import com.caribu.richiesta_orm.model.RichiestaList;

import io.vertx.core.Future;

public interface RichiestaRepository {
    Future<RichiestaDTO> createRequest(RichiestaDTO RichiestaDTO);
    Future<RichiestaDTO> updateRequest(RichiestaDTO RichiestaDTO);
    Future<Optional<RichiestaDTO>> findRequestById(Integer id);
    Future<Void> removeRequest(Integer id);
    Future<RichiestaList> findRequestByClientId(Integer clientId);
}
