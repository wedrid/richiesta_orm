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

import io.netty.handler.codec.http.HttpHeaderValues;
import io.vertx.circuitbreaker.CircuitBreaker;
import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.servicediscovery.ServiceDiscovery;

public class RichiestaController {
    private RichiestaServiceInterface richiestaService;
    private TrattaServiceInterface trattaService;
    private ServiceDiscovery discovery;
    private Vertx vertx;

    public RichiestaController(RichiestaServiceInterface richiestaService, TrattaServiceInterface trattaService, Vertx vertx) {
        this.richiestaService = richiestaService;
        this.trattaService = trattaService;
        this.discovery = ServiceDiscovery.create(vertx);
        this.vertx = vertx;
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
                            
                            
                            //context.response().setStatusCode(201).end();
                            try {
                                JsonObject responseBody = new JsonObject().put("message", "ok");
                                context.response()
                                    .putHeader("Content-Type", "application/json")
                                    .setStatusCode(201)
                                    .end(responseBody.encode());
                                System.out.println("Sent status ok. ");
                             } catch (Exception e) {
                                System.err.println("Error sending response: " + e.getMessage());
                                e.printStackTrace();
                             }
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

    public void addRichiestaUsingCustomerName(RoutingContext context) {
        // Prima di inserire una richiesta, chiedo a filiale di darmi l'id del cliente
        JsonObject body = context.body().asJsonObject();
        System.out.println("Ecco il body: " + body.toString());
        // 0. Get customer name from rest service
        Future<String> customerIdFuture = getCustomerIdFromRestService(body.getString("cliente_name"));
        Future<Optional<Tratta>> trattaFuture = trattaService.getTrattaById(body.getInteger("tratta_id"));

        Future.all(customerIdFuture, trattaFuture).onComplete(ar -> {
            System.out.println("Both futures are completed!!!!");
            var resultAtZero = ar.result().resultAt(0);
            var resultAtTwo = ar.result().resultAt(1);
            System.out.println("Result at zero: " + resultAtZero.toString());
            System.out.println("Result at two: " + resultAtTwo.toString());
            if (ar.succeeded()) {
                Optional<Tratta> trattaOptional = ar.result().resultAt(1);
                int cliente_id = Integer.parseInt(ar.result().resultAt(0));
                trattaOptional.ifPresent(tratta -> {
                    // Use the TrattaDTO object
                    // TODO: Convert the trattaDTO into Tratta using the mapper, then add richiesta.. 
                    System.out.println("Tratta id: " + tratta.getId());
                    System.out.println("Tratta object: " + tratta.toString());

                    RichiestaDTO richiestaDTO = new RichiestaDTO(
                        null, //id 
                        cliente_id, 
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
                            
                            
                            //context.response().setStatusCode(201).end();
                            try {
                                JsonObject responseBody = new JsonObject().put("message", "ok");
                                context.response()
                                    .putHeader("Content-Type", "application/json")
                                    .setStatusCode(201)
                                    .end(responseBody.encode());
                                System.out.println("Sent status ok. ");
                             } catch (Exception e) {
                                System.err.println("Error sending response: " + e.getMessage());
                                e.printStackTrace();
                             }
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

    public Future<String> getCustomerIdFromRestService(String customerName) {

        CircuitBreakerOptions options = new CircuitBreakerOptions()
            .setMaxFailures(5) // number of failure before opening the circuit
            .setTimeout(2000) // consider a failure if the operation does not succeed in time
            .setFallbackOnFailure(true) // do we call the fallback on failure
            .setResetTimeout(10000); // time spent in open state before attempting to re-try

        CircuitBreaker breaker = CircuitBreaker.create("my-circuit-breaker", vertx, options);

        WebClient client = WebClient.create(vertx);

        Future<String> customerIdFuture = breaker.execute(future -> 
            client.getAbs("http://localhost:10010/clientid/" + customerName)
                .send()
                .onSuccess(response -> {
                    System.out.println("Il servizio filiale è disponibile");
                    JsonObject body = response.bodyAsJsonObject();
                    if (body != null && body.containsKey("id")) {
                        String customerId = body.getString("id");
                        System.out.println("Completing promise for customer " + customerName + " id: " + customerId);
                        future.complete(customerId);
                    } else {
                        future.fail("No customer ID found in response");
                    }
                })
                .onFailure(err -> {
                    System.out.println("Il servizio filiale non è disponibile");
                    future.complete("0");
                })
        );

        return customerIdFuture;
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
