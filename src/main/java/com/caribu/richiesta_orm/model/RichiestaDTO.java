package com.caribu.richiesta_orm.model;

import java.time.LocalDateTime;

public class RichiestaDTO {
    private Integer id;
    private Integer idCliente;
    private Integer idTratta;
    private Integer idOperativo;
    private boolean accepted;
    private LocalDateTime createdAt;

    public RichiestaDTO(Integer id, Integer idCliente, Integer idTratta, Integer idOperativo, boolean accepted, LocalDateTime createdAt) {
        this.id = id;
        this.idCliente = idCliente;
        this.idTratta = idTratta;
        this.idOperativo = idOperativo;
        this.accepted = accepted;
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
