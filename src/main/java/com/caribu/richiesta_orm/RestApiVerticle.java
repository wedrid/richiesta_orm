package com.caribu.richiesta_orm;

import com.hazelcast.client.config.ClientConfig;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.sqlclient.Pool;

public class RestApiVerticle extends AbstractVerticle{
    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
            startHttpServerAndAttachRoutes(startPromise);
    }

    private void startHttpServerAndAttachRoutes(Promise<Void> startPromise) {
        RouterBuilder.create(vertx, "openapi.yaml")
            .onSuccess(routerBuilder -> {
                routerBuilder.operation("getAllClients").handler( h -> {}); //these are the controllers
                
            }
            );
    }
}
