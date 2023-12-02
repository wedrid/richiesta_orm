package com.caribu.richiesta_orm.service;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import org.hibernate.reactive.stage.Stage.SessionFactory;
import com.caribu.richiesta_orm.data.RichiestaDTOMapper;
import com.caribu.richiesta_orm.data.RichiestaEntityMapper;
import com.caribu.richiesta_orm.model.Richiesta;
import com.caribu.richiesta_orm.model.RichiestaDTO;

import io.netty.handler.codec.http.HttpResponse;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.servicediscovery.ServiceDiscovery;

public class RichiestaService implements RichiestaServiceInterface{
    private SessionFactory sessionFactory;
    private WebClient webClient;
    private ServiceDiscovery discovery;

    public RichiestaService(SessionFactory sessionFactory, WebClient webClient, ServiceDiscovery discovery) {
        this.sessionFactory = sessionFactory;
        this.webClient = webClient;
        this.discovery = discovery;
    }

    public Future<RichiestaDTO> addRichiesta(RichiestaDTO richiestaDTO) {
        // Da richiesta DTO estrai il company name e chiama il micro servizio filiale per ricevere l'ID
        
        // #### get the cliente name, ask the filiale microservice the id of the client in order to get the FK
        String clientName = richiestaDTO.getNomeCliente();
        
        Future<JsonObject> httpFuture = webClient.get(10005, "127.0.0.1", "/clientid/" + clientName)
            .send()
            .compose(response -> {
                System.out.println(response.bodyAsString());
                if (response.statusCode() == 200) {
                    return Future.succeededFuture(response.bodyAsJsonObject());
                } else {
                    return Future.failedFuture(new Exception("Could not get the id of the client from the filiale microservice"));
                }
        });
        
        Future <RichiestaDTO> future = httpFuture.compose(jsonObject -> {
            // Extract the client ID from the JSON object
            Integer clientId = jsonObject.getInteger("id");
            System.out.println("÷÷÷÷÷÷÷÷÷÷÷÷÷÷÷ Ecco l'id del cliente: " + clientId);
            // Set the client ID in the RichiestaDTO
            richiestaDTO.setIdCliente(clientId);
    
            // Convert the RichiestaDTO to a Richiesta entity
            Richiesta richiestaEntity = new RichiestaEntityMapper().apply(richiestaDTO);
            System.out.println("Ecco la richiesta entity: " + richiestaEntity.toString());
    
            // Persist the Richiesta entity
            CompletionStage<Void> dbResult = sessionFactory.withTransaction((s, t) -> s.persist(richiestaEntity));
    
            // Convert the CompletionStage to a Future and map the result to a RichiestaDTO
            RichiestaDTOMapper dtoMapper = new RichiestaDTOMapper();
            return Future.fromCompletionStage(dbResult).map(v -> dtoMapper.apply(richiestaEntity));
        });

        return future;

        
        // Richiesta richiestaEntity = new RichiestaEntityMapper().apply(richiestaDTO);
        // CompletionStage<Void> result = sessionFactory.withTransaction((s, t) -> s.persist(richiestaEntity));
        // RichiestaDTOMapper dtoMapper = new RichiestaDTOMapper(); 
        // Future<RichiestaDTO> future = Future.fromCompletionStage(result).map(v -> dtoMapper.apply(richiestaEntity));
        // return future;
    }

    public Future<RichiestaDTO> getRichiestaById(Integer id) {
        CompletionStage<Richiesta> result = sessionFactory.withSession(s -> s.find(Richiesta.class, id));
        System.out.println("Pippo");
        RichiestaDTOMapper dtoMapper = new RichiestaDTOMapper(); 
        System.out.println("Pluto");
        Future<RichiestaDTO> future = Future.fromCompletionStage(result).map(v -> dtoMapper.apply(v));
        System.out.println("Paperino");
        return future;
    }

}
