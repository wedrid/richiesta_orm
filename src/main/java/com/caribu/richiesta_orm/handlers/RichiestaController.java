package com.caribu.richiesta_orm.handlers;

import java.time.LocalDateTime;

import com.caribu.richiesta_orm.model.RichiestaDTO;
import com.caribu.richiesta_orm.service.RichiestaService;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class RichiestaController {
    private RichiestaService richiestaService;
    public RichiestaController(RichiestaService richiestaService) {
        this.richiestaService = richiestaService;
    }

    public void createRequest(RoutingContext context) {
        // Mi immagino una situazione nella quale l'id della tratta mi viene passato (e.g. da un front end che prima richiede le tratte)
        JsonObject body = context.body().asJsonObject();
        System.out.println("Ecco il body: " + body.toString());
        // 1. Transform the json to DTO
        RichiestaDTO richiestaDTO = new RichiestaDTO(
            null,
            body.getInteger("client_id"),
            body.getInteger("operativo_id"),
            

        );

        // 2. Call service
        // 3. Return response
        TrattaDTO tratta = new TrattaDTO(
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
