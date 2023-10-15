package com.caribu.richiesta_orm.data;

import java.util.Optional;

import com.caribu.richiesta_orm.model.RequestDTO;
import com.caribu.richiesta_orm.model.RequestsList;

import io.vertx.core.Future;

public class RequestRepositoryImpl implements RequestRepository{

    @Override
    public Future<RequestDTO> createRequest(RequestDTO requestDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createRequest'");
    }

    @Override
    public Future<RequestDTO> updateRequest(RequestDTO requestDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateRequest'");
    }

    @Override
    public Future<Optional<RequestDTO>> findRequestById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findRequestById'");
    }

    @Override
    public Future<Void> removeRequest(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeRequest'");
    }

    @Override
    public Future<RequestsList> findRequestByClientId(Integer clientId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findRequestByClientId'");
    }
    
}
