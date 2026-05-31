package com.museum.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;

@SpringBootTest
@ImportTestcontainers(PostgresTestcontainersConfiguration.class)
class MuseumApplicationTests {

    @Test
    void contextLoads() {
    }
}
