package com.caribu.richiesta_orm.model;

import java.time.LocalDateTime;

import io.vertx.core.json.JsonObject;

public class RichiestaDTO {
    private Integer id;
    private Integer idCliente;
    private Tratta tratta;
    private Integer idOperativo;
    private String nomeCliente;


    public String getnomeCliente() {
        return nomeCliente;
    }

    public void setnomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    private boolean accepted;

    public RichiestaDTO(Integer id, Integer idCliente, String nomeCliente, Tratta tratta, Integer idOperativo, boolean accepted, LocalDateTime createdAt) {
        this.idCliente = idCliente;
        this.nomeCliente = nomeCliente;
        this.tratta = tratta;
        this.idOperativo = idOperativo;
        this.accepted = accepted;
        this.createdAt = createdAt;
        System.out.println("DTO: id cliente " + idCliente + " id operativo " + idOperativo + " accepted " + accepted + " created at " + createdAt);
    }

    public Tratta getTratta() {
        return tratta;
    }

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
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
/* 
    public Integer getIdTratta() {
        return idTratta;
    }

    public void setIdTratta(Integer idTratta) {
        this.idTratta = idTratta;
    }
*/

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

    private LocalDateTime createdAt;

    

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.put("id", this.id);
        json.put("nomeCliente", this.nomeCliente);
        json.put("idCliente", this.idCliente);
        json.put("tratta", this.tratta.toJson());
        json.put("idOperativo", this.idOperativo);
        json.put("accepted", this.accepted);
        json.put("createdAt", this.createdAt.toString());
        return json;
    }
}
