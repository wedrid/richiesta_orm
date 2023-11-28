package com.caribu.richiesta_orm.service;

import com.caribu.richiesta_orm.model.RichiestaDTO;

import io.vertx.core.Future;

public interface RichiestaServiceInterface {
    public Future<RichiestaDTO> addRichiesta(RichiestaDTO richiestaDTO);

    public Future<RichiestaDTO> getRichiestaById(Integer id);
}
