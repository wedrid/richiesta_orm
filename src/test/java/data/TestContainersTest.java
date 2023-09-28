package data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.junit.jupiter.Container;

@Testcontainers
class TestContainersTest {
    @Container
    PostgreSQLContainer container = new PostgreSQLContainer("postgres:13-alpine")
        .withDatabaseName("testcontainersdb")
        .withUsername("tcuser")
        .withPassword("secret");

    @Test
    void testContainersIsRunningTest(){
        Assertions.assertTrue(container.isCreated());
        Assertions.assertTrue(container.isRunning());
    }
}
