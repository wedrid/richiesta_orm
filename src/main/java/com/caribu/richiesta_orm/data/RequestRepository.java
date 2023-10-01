package com.caribu.richiesta_orm.data;

import java.util.Optional;

import com.caribu.richiesta_orm.model.RequestDTO;
import com.caribu.richiesta_orm.model.RequestsList;

import io.vertx.core.Future;

public interface RequestRepository {
    Future<RequestDTO> createRequest(RequestDTO requestDTO);
    Future<RequestDTO> updateRequest(RequestDTO requestDTO);
    Future<Optional<RequestDTO>> findRequestById(Integer id);
    Future<Void> removeRequest(Integer id);
    Future<RequestsList> findRequestByClientId(Integer clientId);
}
