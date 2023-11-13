package com.caribu.richiesta_orm.controllers;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class AddNewTrattaHandler implements Handler<RoutingContext>{
    @Override
    public void handle(RoutingContext context) {
        // Questo lo chiamo handler, di fatto è il controller
        // Per ora faccio tutto qua, poi TODO: da spezzettare 
        
        // per aggiungere una tratta ho il modell a cui servono: latitudine e longitudine di destinazione e origine
        // e poi il nome della tratta (?)
        String body = context.body().asString();
        context.response().end(new JsonObject().put("message", "OK").toString());
    }
}