package com.caribu.richiesta_orm.data;

import java.util.function.Function;

import com.caribu.richiesta_orm.model.Richiesta;
import com.caribu.richiesta_orm.model.RichiestaDTO;

public class RichiestaDTOMapper implements Function<Richiesta, RichiestaDTO>{

    @Override
    public RichiestaDTO apply(Richiesta request) {
        return new RichiestaDTO(
            request.getId(),
            request.getIdCliente(), 
            request.getTratta(),
            request.getIdOperativo(), 
            request.isAccepted(), 
            request.getDataInserimento());
    }
}