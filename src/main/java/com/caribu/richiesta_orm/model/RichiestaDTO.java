package com.caribu.richiesta_orm.model;

import java.time.LocalDateTime;

public class RichiestaDTO {
    private Integer id;
    private Integer idCliente;
    private Integer idOperativo;
    private boolean accepted;
    private LocalDateTime createdAt;

    public RichiestaDTO(Integer id, Integer idCliente, Integer idOperativo, boolean accepted, LocalDateTime createdAt) {
        this.id = id;
        this.idCliente = idCliente;
        this.idOperativo = idOperativo;
        this.accepted = accepted;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdOperativo() {
        return idOperativo;
    }

    public void setIdOperativo(Integer idOperativo) {
        this.idOperativo = idOperativo;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
