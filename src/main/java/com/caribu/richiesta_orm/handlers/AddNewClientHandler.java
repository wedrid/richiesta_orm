package com.caribu.richiesta_orm.handlers;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class AddNewClientHandler implements Handler<RoutingContext>{

    @Override
    public void handle(RoutingContext context) {
        context.request().bodyHandler(bodyHandler -> {
            String body = bodyHandler.toString();
            System.out.println(body);
            System.out.println("Eccolo");
        });
    }
    
}
