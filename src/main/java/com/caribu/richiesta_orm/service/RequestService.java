package com.caribu.richiesta_orm.service;
import com.caribu.richiesta_orm.model.RequestDTO;
import com.caribu.richiesta_orm.model.RequestsList;

import io.vertx.core.Future;

public interface RequestService {
    // define operations that will be implemented in the service implementation
    // these are just two examples
    Future<RequestDTO> createRequest(RequestDTO projectDTO); // this will call the filiale microservice to get the client id
    Future<RequestsList> getRequestsByClienteId(Integer clienteId);
}
