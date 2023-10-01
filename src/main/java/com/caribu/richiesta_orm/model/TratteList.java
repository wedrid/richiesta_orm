package com.caribu.richiesta_orm.model;

import java.util.List;

public class TratteList {
    private List<TrattaDTO> tratte;
    
    public TratteList(List<TrattaDTO> tratte) {
        this.tratte = tratte;
    }

    public List<TrattaDTO> getTratte() {
        return tratte;
    }

    public void setTratte(List<TrattaDTO> tratte) {
        this.tratte = tratte;
    }
}
