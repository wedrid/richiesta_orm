package com.caribu.richiesta_orm;

import org.hibernate.reactive.stage.Stage.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caribu.richiesta_orm.handlers.AddNewTrattaHandler;
import com.caribu.richiesta_orm.handlers.TrattaController;
import com.caribu.richiesta_orm.service.RichiestaService;
import com.caribu.richiesta_orm.service.TrattaService;
import com.hazelcast.client.config.ClientConfig;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.sqlclient.Pool;

public class RestApiVerticle extends AbstractVerticle{
    private static final Logger LOG = LoggerFactory.getLogger(RestApiVerticle.class);
    public static final int HTTP_PORT = 10001;
    private SessionFactory sessionFactory;
    private TrattaService trattaService;
    private RichiestaService richiestaService;

    public RestApiVerticle(String name, SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
        System.out.println("Hello verticle #######" + name);
        //Inject SessionFactories
        trattaService = new TrattaService(sessionFactory); 
        richiestaService = new RichiestaService(sessionFactory);
    }

    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
        // HttpServer server = vertx.createHttpServer();
        // Router router = Router.router(vertx);
        // router.route("/*").handler(BodyHandler.create());

        // router.get("/ping").handler(context -> {
        //     context.response().end(new JsonObject().put("message", "pong").toString());
        //   });

        // router.post("/clients").handler(context -> {
        //     String body = context.body().asString();
        //     System.out.println(body);
        //     /* 
        //     JsonObject body = context.getBodyAsJson();
        //     System.out.println(body.toString());
        //     */
        //     context.response().setStatusCode(201).end(new JsonObject().put("message", "pong").toString());
        // });
        // server.requestHandler(router).listen(HTTP_PORT).onSuccess(result -> startPromise.complete())
        //     .onFailure(err -> startPromise.fail(err));
        System.out.println("Starting http server and attaching routes");
        startHttpServerAndAttachRoutes(startPromise);
        
    }

    //il successivo è per openAPI ma mi sta dando problemi, provo prima strada senza, poi vediamo
    private void startHttpServerAndAttachRoutes(Promise<Void> startPromise) {
        System.out.println("Hello verticle ################");
        RouterBuilder.create(vertx, "openapi.yaml")
            .onSuccess(routerBuilder -> {
                routerBuilder.operation("ping").handler( h -> {
                    h.response().end(new JsonObject().put("message", "ping pong ####").toString());
                });
                //Posso fare qui le DI nel costruttore
                
                TrattaController trattaController = new TrattaController(trattaService);
                RichiestaController richiestaController = new RichiestaController(controllerService);

                routerBuilder.operation("addNewTratta").handler(ctx -> trattaController.addNewTratta(ctx));
                routerBuilder.operation("createNewRichiesta").handler(ctx -> richiestaController.createNewRichiesta(ctx));
                

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
