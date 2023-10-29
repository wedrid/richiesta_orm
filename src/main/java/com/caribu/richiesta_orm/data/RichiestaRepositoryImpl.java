package com.caribu.richiesta_orm.data;

import java.util.Optional;

import com.caribu.richiesta_orm.model.RichiestaDTO;
import com.caribu.richiesta_orm.model.RichiestaList;

import io.vertx.core.Future;

public class RichiestaRepositoryImpl implements RichiestaRepository{

    @Override
    public Future<RichiestaDTO> createRequest(RichiestaDTO RichiestaDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createRequest'");
    }

    @Override
    public Future<RichiestaDTO> updateRequest(RichiestaDTO RichiestaDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateRequest'");
    }

    @Override
    public Future<Optional<RichiestaDTO>> findRequestById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findRequestById'");
    }

    @Override
    public Future<Void> removeRequest(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeRequest'");
    }

    @Override
    public Future<RichiestaList> findRequestByClientId(Integer clientId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findRequestByClientId'");
    }
    
}
