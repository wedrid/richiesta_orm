package com.caribu.richiesta_orm.model;

import java.util.List;

public class RichiestaList {
    private List<RichiestaDTO> richieste;

    public RichiestaList(List<RichiestaDTO> richieste) {
        this.richieste = richieste;
    }

    public List<RichiestaDTO> getrichieste() {
        return richieste;
    }

    public void setrichieste(List<RichiestaDTO> richieste) {
        this.richieste = richieste;
    }
}
