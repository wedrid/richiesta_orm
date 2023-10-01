package com.caribu.richiesta_orm.model;

import java.time.LocalDateTime;

public class TrattaDTO {
    private Integer id;
    private Float lat;
    private Float lon;
    private LocalDateTime dataInserimento;
    public TrattaDTO(Integer id, Float lat, Float lon, LocalDateTime dataInserimento) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.dataInserimento = dataInserimento;
    }
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
