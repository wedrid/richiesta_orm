package com.caribu.richiesta_orm.model;

import java.time.LocalDateTime;

import io.vertx.core.json.JsonObject;

public class TrattaDTO {
    private Integer id;
    private Float origLat;
    private Float origLon;
    public Float getOrigLat() {
        return origLat;
    }

    public void setOrigLat(Float origLat) {
        this.origLat = origLat;
    }

    public Float getOrigLon() {
        return origLon;
    }

    public void setOrigLon(Float origLon) {
        this.origLon = origLon;
    }
    private Float destLat;
    public Float getorigLat() {
        return origLat;
    }

    public void setorigLat(Float origLat) {
        this.origLat = origLat;
    }

    public Float getorigLon() {
        return origLon;
    }

    public void setorigLon(Float origLon) {
        this.origLon = origLon;
    }

    public Float getDestLat() {
        return destLat;
    }

    public void setDestLat(Float destLat) {
        this.destLat = destLat;
    }

    public Float getDestLon() {
        return destLon;
    }

    public void setDestLon(Float destLon) {
        this.destLon = destLon;
    }
    private Float destLon;
    private LocalDateTime dataInserimento;
    public TrattaDTO(Integer id, Float origLat, Float origLon, Float destLat, Float destLon, LocalDateTime dataInserimento) {
        this.id = id;
        this.origLat = origLat;
        this.origLon = origLon;
        this.destLat = destLat; 
        this.destLon = destLon;
        this.dataInserimento = dataInserimento;
    }
    
    public LocalDateTime getDataInserimento() {
        return dataInserimento;
    }
    public void setDataInserimento(LocalDateTime dataInserimento) {
        this.dataInserimento = dataInserimento;
    }


    public Integer getId() {
        return id;
    }



    public void setId(Integer id) {
        this.id = id;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.put("id", this.id);
        json.put("origLat", this.origLat);
        json.put("origLon", this.origLon);
        json.put("destLat", this.destLat);
        json.put("destLon", this.destLon);
        json.put("dataInserimento", this.dataInserimento.toString());
        return json;
    }
}
