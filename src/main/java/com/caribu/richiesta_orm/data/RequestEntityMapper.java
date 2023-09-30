package com.caribu.richiesta_orm.data;

import java.util.function.Function;

import com.caribu.richiesta_orm.model.Request;
import com.caribu.richiesta_orm.model.RequestDTO;

public class RequestEntityMapper implements Function<RequestDTO, Request>{

    @Override
    public Request apply(RequestDTO reqDTO) {
        Request request = new Request();
        request.setId(reqDTO.getId());
        request.setIdCliente(reqDTO.getIdCliente());
        request.setIdOperativo(reqDTO.getIdOperativo());
        request.setAccepted(reqDTO.isAccepted());
        request.setDataInserimento(reqDTO.getCreatedAt());
        return request;
    }
    
}
