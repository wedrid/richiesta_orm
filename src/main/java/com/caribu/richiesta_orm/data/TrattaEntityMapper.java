package com.caribu.richiesta_orm.data;

import java.util.function.Function;

import com.caribu.richiesta_orm.model.Tratta;
import com.caribu.richiesta_orm.model.TrattaDTO;

public class TrattaEntityMapper implements Function<TrattaDTO, Tratta> {
    @Override
    public Tratta apply(TrattaDTO t) {
        Tratta tratta = new Tratta();
        tratta.setorigLat(t.getOrigLat());
        tratta.setorigLon(t.getOrigLon());
        tratta.setdestLat(t.getDestLat());
        tratta.setdestLon(t.getDestLon());
        return tratta;
    }
}