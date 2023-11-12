package com.caribu.richiesta_orm.data;

import java.util.function.Function;

import com.caribu.richiesta_orm.model.Tratta;
import com.caribu.richiesta_orm.model.TrattaDTO;

public class TrattaDTOMapper implements Function<Tratta, TrattaDTO> {

    @Override
    public TrattaDTO apply(Tratta t) {
        //TODO: change the first argument based on t.id()
        return new TrattaDTO(null, t.getorigLat(), t.getorigLon(), t.getdestLat(), t.getdestLon(), t.getDataInserimento());
    }
}