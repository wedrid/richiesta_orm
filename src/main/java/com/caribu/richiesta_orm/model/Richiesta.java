package com.caribu.richiesta_orm.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "richiesta")
public class Richiesta {
    @Id @GeneratedValue
    private Integer id;
    private Integer idCliente;
    //private String nomeCliente; non è corretto che ci sia il nome

    private Integer idOperativo; 
    private boolean accepted;
    @ManyToOne @JoinColumn(name="trattaId", nullable=true)
    private Tratta tratta;
    private LocalDateTime dataInserimento;

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public void setId(Integer id){
        //this.id = id;
    }

    // public String getNomeCliente() {
    //     return nomeCliente;
    // }

    // public void setNomeCliente(String nomeCliente) {
    //     this.nomeCliente = nomeCliente;
    // }

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

    public Integer getIdTratta() {
        if(tratta == null){
            return -1;
        } else {
            return tratta.getId();
        }
        
    }

    public Tratta getTratta() {
        return tratta;
    }

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
    }
}
