package com.caribu.richiesta_orm.data;

import java.util.function.Function;

import com.caribu.richiesta_orm.model.Request;
import com.caribu.richiesta_orm.model.RequestDTO;

public class RequestDTOMapper implements Function<Request, RequestDTO>{

    @Override
    public RequestDTO apply(Request request) {
        return new RequestDTO(request.getId(), request.getIdCliente(), request.getIdOperativo(), request.isAccepted(), request.getDataInserimento());
    }
    
}
