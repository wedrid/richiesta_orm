package com.caribu.richiesta_orm.handlers;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class AddNewClientHandler implements Handler<RoutingContext>{

    @Override
    public void handle(RoutingContext context) {
        context.request().bodyHandler(body -> {
            JsonObject json = body.toJsonObject();
            String name = json.getString("name");
            System.out.println("Name: " + name);
        });
    }
    
}
