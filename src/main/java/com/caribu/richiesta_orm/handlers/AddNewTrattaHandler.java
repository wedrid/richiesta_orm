package com.caribu.richiesta_orm.handlers;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class AddNewTrattaHandler implements Handler<RoutingContext>{
    @Override
    public void handle(RoutingContext context) {
        System.out.println("Pre body handler");
        String body = context.body().asString();
        System.out.println("miao");
        System.out.println(body);
        context.response().end(new JsonObject().put("message", "OK").toString());
    }
    
}