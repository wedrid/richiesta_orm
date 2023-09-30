package com.caribu.richiesta_orm.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "request")
public class Request {
    @Id @GeneratedValue
    private Integer id;
    private Integer idCliente;
    private Integer idOperativo; 
    private boolean accepted;
    @ManyToOne @JoinColumn(name="projectId", nullable=true)
    private Tratta tratta;


    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    private LocalDateTime dataInserimento;

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return id;
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

    public LocalDateTime getDataInserimento() {
        return dataInserimento;
    }

    public void setDataInserimento(LocalDateTime dataInserimento) {
        this.dataInserimento = dataInserimento;
    }

    public Tratta getTratta() {
        return tratta;
    }

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
    }

    

    

}