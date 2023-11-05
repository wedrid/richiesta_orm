package com.caribu.richiesta_orm.data;

import java.util.function.Function;

import com.caribu.richiesta_orm.model.Richiesta;
import com.caribu.richiesta_orm.model.RichiestaDTO;

public class RichiestaEntityMapper implements Function<RichiestaDTO, Richiesta>{

    @Override
    public Richiesta apply(RichiestaDTO reqDTO) {
        Richiesta request = new Richiesta();
        request.setId(reqDTO.getId());
        request.setIdCliente(reqDTO.getIdCliente());
        request.setIdOperativo(reqDTO.getIdOperativo());
        request.setAccepted(reqDTO.isAccepted());
        request.setDataInserimento(reqDTO.getCreatedAt());
        return request;
    }
}
