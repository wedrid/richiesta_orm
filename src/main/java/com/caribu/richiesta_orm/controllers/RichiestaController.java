package com.caribu.richiesta_orm.controllers;
import java.time.LocalDateTime;
import java.util.Optional;

import com.caribu.richiesta_orm.model.RichiestaDTO;
import com.caribu.richiesta_orm.model.Tratta;
import com.caribu.richiesta_orm.model.TrattaDTO;
import com.caribu.richiesta_orm.service.RichiestaService;
import com.caribu.richiesta_orm.service.RichiestaServiceInterface;
import com.caribu.richiesta_orm.service.TrattaService;
import com.caribu.richiesta_orm.service.TrattaServiceInterface;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;
import io.vertx.servicediscovery.ServiceDiscovery;

public class RichiestaController {
    private RichiestaServiceInterface richiestaService;
    private TrattaServiceInterface trattaService;

    public RichiestaController(RichiestaServiceInterface richiestaService, TrattaServiceInterface trattaService, Vertx vertx) {
        this.richiestaService = richiestaService;
        this.trattaService = trattaService;
        
    }

    public void addRichiesta(RoutingContext context) {
        // Mi immagino una situazione nella quale l'id della tratta mi viene passato (e.g. da un front end che prima richiede le tratte)
        JsonObject body = context.body().asJsonObject();
        System.out.println("Ecco il body: " + body.toString());
        // 1. Transform the json to DTO
        //retrieve the tratta from the db
        Future<Optional<Tratta>> trattaFuture = trattaService.getTrattaById(body.getInteger("tratta_id"));
        System.out.println("Before onComplete");
        trattaFuture.onComplete(ar -> {
            System.out.println("In onComplete");
            if (ar.succeeded()) {
                Optional<Tratta> trattaOptional = ar.result();
                trattaOptional.ifPresent(tratta -> {
                    // Use the TrattaDTO object
                    // TODO: Convert the trattaDTO into Tratta using the mapper, then add richiesta.. 
                    System.out.println("Tratta id: " + tratta.getId());
                    System.out.println("Tratta object: " + tratta.toString());

                    RichiestaDTO richiestaDTO = new RichiestaDTO(
                        null, //id 
                        null, 
                        body.getString("cliente_name"),
                        tratta, //tratta TODO: add tratta 
                        body.getInteger("operativo_id"), 
                        false, //default accepted false, then can be accepted later 
                        LocalDateTime.now() 
                    );
                    richiestaService.addRichiesta(richiestaDTO)
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

                });
            } else {
                // Handle the error
                System.out.println("Failed to get Tratta: " + ar.cause().getMessage());
            }
        });
        /* 
        RichiestaDTO richiestaDTO = new RichiestaDTO(
            null, //id 
            body.getInteger("cliente_id"), 
            null, //tratta TODO: add tratta 
            body.getInteger("operativo_id"), 
            false, //default accepted false, then can be accepted later 
            LocalDateTime.now() 
        );

        // 2. Call service
        richiestaService.addRichiesta(richiestaDTO)
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
                });*/
    }

    public void getRichiestaById(RoutingContext context){
        Integer richiestaId = Integer.valueOf(context.pathParam("richiestaId"));
        System.out.println("Getting righiesta of id: " + richiestaId);
        richiestaService.getRichiestaById(richiestaId)
            .onSuccess(result -> {
                JsonObject body = result.toJson();
                context.response().setStatusCode(200).end(body.encode());
            })
            .onFailure(err -> context.response().setStatusCode(500).end());

        
        // Integer userId = Integer.valueOf(context.pathParam("userId"));
        // projectService.findProjectsByUser(userId)
        // .onSuccess(result -> {
        //   JsonObject body = JsonObject.mapFrom(result);
        //   context.response().setStatusCode(200).end(body.encode());
        // })
        // .onFailure(err -> context.response().setStatusCode(500).end());
    }
}
