package data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.concurrent.CompletionStage;

import javax.imageio.spi.ServiceRegistry;

import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder;
import org.hibernate.reactive.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.caribu.richiesta_orm.model.Task;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

@ExtendWith(VertxExtension.class)
class HibernateConfigurationTest {
    @Test
    void initializeHibernateWithCodeTest(Vertx vertx, VertxTestContext context){
        // 1. Create properties with config data 
        Properties hibernateProps = new Properties();
        // url
        hibernateProps.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/requestorm");
        // credentials
        hibernateProps.put("hibernate.connection.username", "reqormuser");
        hibernateProps.put("hibernate.connection.password", "secret");

        // schema generation
        hibernateProps.put("jakarta.persistence.schema-generation.database.action", "create");
        //hibernateProps.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");


        // 2. Create Hibernate Configuration 
        Configuration hibernateConfiguration = new Configuration(); //Java equivalent to xml configuration
        hibernateConfiguration.setProperties(hibernateProps);
        hibernateConfiguration.addAnnotatedClass(Task.class);

        // 3. Create Service Registry
        StandardServiceRegistry serviceRegistry = new ReactiveServiceRegistryBuilder()
            .applySettings(hibernateConfiguration.getProperties())
            .build();

        // 4. Create Session Factory //non abbiamo accesso diretto al db ma usiamo un intermediario
        Stage.SessionFactory sessionFactory = hibernateConfiguration
            .buildSessionFactory(serviceRegistry).unwrap(Stage.SessionFactory.class);
    

        // Do something with db
        Task task = new Task();
        task.setContent("Hello, this is a new task");
        task.setCompleted(false);
        task.setUserId(1);
        task.setCreatedAt(LocalDateTime.now());

        System.out.println("Task id before insertion is: " + task.getId());

        CompletionStage<Void> insertionResult = sessionFactory
            .withTransaction((s, t) -> s.persist(task));

        Future<Void> future = Future.fromCompletionStage(insertionResult);
        context.verify(() -> future.onFailure(err -> context.failNow(err))
            .onSuccess(v -> {
                System.out.println("Task id after insertion is: " + task.getId());
                context.completeNow();
            }));
    }
}
