package com.caribu.richiesta_orm.model;

import java.time.LocalDateTime;

import io.vertx.core.json.JsonObject;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Entity
@Table(name = "tratta")
public class Tratta {
    @Id @GeneratedValue
    private Integer id;
    private Float originLat;
    private Float originLon;

    private Float destLat; 
    private Float destLon;
    private LocalDateTime dataInserimento;
    // because tratta does not have to be aware of the requests, no hibernate association, not variable is needed (and wanted)
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Float getorigLat() {
        return originLat;
    }
    public void setorigLat(Float originLat) {
        this.originLat = originLat;
    }
    public Float getorigLon() {
        return originLon;
    }
    public void setorigLon(Float originLon) {
        this.originLon = originLon;
    }
    public Float getdestLat() {
        return destLat;
    }
    public void setdestLat(Float destLat) {
        this.destLat = destLat;
    }
    public Float getdestLon() {
        return destLon;
    }
    public void setdestLon(Float destLon) {
        this.destLon = destLon;
    }

    public LocalDateTime getDataInserimento() {
        return dataInserimento;
    }
    public void setDataInserimento(LocalDateTime dataInserimento) {
        this.dataInserimento = dataInserimento;
    }

    public JsonObject toJson(){
        JsonObject json = new JsonObject();
        json.put("id", this.id);
        json.put("originLat", this.originLat);
        json.put("originLon", this.originLon);
        json.put("destLat", this.destLat);
        json.put("destLon", this.destLon);
        json.put("dataInserimento", this.dataInserimento);
        return json;
    }
    
}
