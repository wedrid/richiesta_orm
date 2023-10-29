package com.caribu.richiesta_orm;
import java.util.Properties;
import org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder;
import org.hibernate.reactive.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.caribu.richiesta_orm.model.Tratta;
import com.caribu.richiesta_orm.model.Richiesta;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

public class MainVerticle extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);
  public static final int PORT = 10001;
  public static final int PROCESSORS = 1; //Runtime.getRuntime().availableProcessors();
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    //TODO: check correctness
    LOG.info("Starting {} with {} processors", MainVerticle.class.getSimpleName(), PROCESSORS);
    deployRestApiVerticle(startPromise);
  }

  private Future<String> deployRestApiVerticle(Promise<Void> startPromise) {
    RestApiVerticle restApiVerticle = new RestApiVerticle("Ciao");
    return vertx.deployVerticle(restApiVerticle,
    new DeploymentOptions().setInstances(PROCESSORS))
      .onFailure(startPromise::fail)
      .onSuccess(id -> {
        LOG.info("Deployed {} with id {}", RestApiVerticle.class.getSimpleName(), id);
        startPromise.complete();
      });
  }

  public static void main(String[] args) {
    ClusterManager mgr = new HazelcastClusterManager();
    VertxOptions options = new VertxOptions().setClusterManager(mgr);

    // Hibernate and other configuration

    // 1. Hibernate configuration
    Properties hibernateProps = new Properties();
    hibernateProps.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/requestorm");
   
     // credentials
    hibernateProps.put("hibernate.connection.username", "reqormuser");
    hibernateProps.put("hibernate.connection.password", "secret");

    hibernateProps.put("javax.persistence.schema-generation.database.action", "create");
    //hibernateProps.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
    Configuration hibernateConfiguration = new Configuration();
    hibernateConfiguration.setProperties(hibernateProps);
    hibernateConfiguration.addAnnotatedClass(Tratta.class);
    hibernateConfiguration.addAnnotatedClass(Richiesta.class);

    // 2. Session factroy
    ServiceRegistry serviceRegistry = new ReactiveServiceRegistryBuilder()
      .applySettings(hibernateConfiguration.getProperties()).build();
    Stage.SessionFactory sessionFactory = hibernateConfiguration
      .buildSessionFactory(serviceRegistry).unwrap(Stage.SessionFactory.class);

    // 3. Instanciate tratta and inject the session factory TODO:
    

    // 4. Instanciate richiesta (and do DI) TODO:

    //DeploymentOptions options = new DeploymentOptions();
    JsonObject config = new JsonObject();
    config.put("port", 8888);
    //options.setConfig(config);

    // Deploy verticle
    Vertx
      .clusteredVertx(options, cluster -> {
       if (cluster.succeeded()) {
           cluster.result().deployVerticle(new MainVerticle(), res -> {
               if(res.succeeded()){
                   LOG.info("Deployment id is: " + res.result());
               } else {
                   LOG.error("Deployment failed!");
               }
           });
       } else {
           LOG.error("Cluster up failed: " + cluster.cause());
       }
    });
  }
}
