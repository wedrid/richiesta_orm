package com.caribu.richiesta_orm.controllers;

import java.time.LocalDateTime;

import com.caribu.richiesta_orm.model.Tratta;
import com.caribu.richiesta_orm.model.TrattaDTO;
import com.caribu.richiesta_orm.service.TrattaService;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class TrattaController {
    private TrattaService trattaService;
    public TrattaController(TrattaService trattaService) {
        this.trattaService = trattaService;
    }
    public void addNewTratta(RoutingContext context) {
        // Questo lo chiamo handler, di fatto è il controller
        // Per ora faccio tutto qua, poi TODO: da spezzettare
        // per aggiungere una tratta ho il modell a cui servono: latitudine e longitudine di destinazione e origine
        // e poi il nome della tratta (?)
        JsonObject body = context.body().asJsonObject();
        System.out.println("Ecco il body: " + body.toString());
        TrattaDTO tratta = new TrattaDTO(
            null,
            body.getFloat("orig_lat"), 
            body.getFloat("orig_lon"),
            body.getFloat("dest_lat"),
            body.getFloat("dest_lon"),
            LocalDateTime.now()
        );
        // Call tratta service and pass the tratta
        trattaService.addTratta(tratta)
            .onSuccess(result -> {
                System.out.println("Successo");
                //JsonObject responseBody = JsonObject.mapFrom(result);
                System.out.println("Successo 2");
                //responseBody.put("Status", "Okay!!");
                System.out.println("Successo 3");
                //context.response().end(new JsonObject().put("message", "OK").toString());
                context.response().setStatusCode(201).end();
                })
            .onFailure(err -> {
                System.out.println("Errore");
                System.out.println(err.getMessage());
                context.response().setStatusCode(500).end();
                });
            
        //context.response().end(new JsonObject().put("message", "OK").toString());
    }

}
