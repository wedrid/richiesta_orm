package com.caribu.richiesta_orm.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Entity
@Table(name = "tratta")
public class Tratta {
    @Id @GeneratedValue
    private Integer id;
    private Float lat;
    private Float lon;
    private LocalDateTime dataInserimento;
    // because tratta does not have to be aware of the requests, no hibernate association, not variable is needed (and wanted)
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Float getLat() {
        return lat;
    }
    public void setLat(Float lat) {
        this.lat = lat;
    }
    public Float getLon() {
        return lon;
    }
    public void setLon(Float lon) {
        this.lon = lon;
    }
    public LocalDateTime getDataInserimento() {
        return dataInserimento;
    }
    public void setDataInserimento(LocalDateTime dataInserimento) {
        this.dataInserimento = dataInserimento;
    }
    
    

}
