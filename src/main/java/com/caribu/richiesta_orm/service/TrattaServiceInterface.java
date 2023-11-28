package com.caribu.richiesta_orm.service;

import java.util.Optional;

import com.caribu.richiesta_orm.model.Tratta;
import com.caribu.richiesta_orm.model.TrattaDTO;

import io.vertx.core.Future;

public interface TrattaServiceInterface {
    public Future<TrattaDTO> addTratta(TrattaDTO trattaDTO);
    public Future<Optional<Tratta>> getTrattaById(Integer id);
}
