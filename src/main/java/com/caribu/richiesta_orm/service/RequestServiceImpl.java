package com.caribu.richiesta_orm.service;

import com.caribu.richiesta_orm.model.RequestDTO;
import com.caribu.richiesta_orm.model.RequestsList;

import io.vertx.core.Future;

public class RequestServiceImpl implements RequestService{

    @Override
    public Future<RequestDTO> createRequest(RequestDTO projectDTO) {
        throw new UnsupportedOperationException("Unimplemented method 'createRequest'");
    }

    @Override
    public Future<RequestsList> getRequestsByClienteId(Integer clienteId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRequestsByClienteId'");
    }
    
}
