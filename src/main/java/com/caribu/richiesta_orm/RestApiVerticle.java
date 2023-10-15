package com.caribu.richiesta_orm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caribu.richiesta_orm.handlers.AddNewClientHandler;
import com.hazelcast.client.config.ClientConfig;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.sqlclient.Pool;

public class RestApiVerticle extends AbstractVerticle{
    private static final Logger LOG = LoggerFactory.getLogger(RestApiVerticle.class);
    public static final int HTTP_PORT = 10001;
    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
            startHttpServerAndAttachRoutes(startPromise);
    }

    private void startHttpServerAndAttachRoutes(Promise<Void> startPromise) {
        System.out.println("Hello verticle ################");
        RouterBuilder.create(vertx, "openapi.yaml")
            .onSuccess(routerBuilder -> {
                routerBuilder.operation("ping").handler( h -> {
                    h.response().end(new JsonObject().put("message", "ping pong").toString());
                });
                routerBuilder.operation("addNewClient").handler(new AddNewClientHandler()); 
                routerBuilder.operation("getAllClients").handler( h -> {}); 
                Router restApi = routerBuilder.createRouter();

                //Create HTTP server and attach routes
                vertx.createHttpServer()
                    .requestHandler(restApi)
                    .listen(HTTP_PORT, ar -> {
                        if(ar.succeeded()) {
                            LOG.info("HTTP server running on port {}", HTTP_PORT);
                            startPromise.complete();
                        } else {
                            LOG.error("Could not start a HTTP server", ar.cause());
                            startPromise.fail(ar.cause());
                        }
                    });
                }
            );
    }


    private void failureHandler(RoutingContext errorContext) {
        
        if (errorContext.response().ended()) {
          // Ignore completed response
          LOG.info("------");
          return;
        }
        LOG.info("Route Error:", errorContext.failure());
        errorContext.response()
          .setStatusCode(500)
          .end(new JsonObject().put("message: Something went wrong, path: ", errorContext.normalizedPath()).toString());
    }
}
