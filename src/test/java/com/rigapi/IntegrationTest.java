package com.rigapi;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.rigapi.testmixin.ApiClientTestMixin;
import com.rigapi.testmixin.DatabaseCleaningTestMixin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("TEST")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = RigApiApplication.class)
public abstract class IntegrationTest implements DatabaseCleaningTestMixin, ApiClientTestMixin {

  @BeforeEach
  void beforeEachTest() {
    cleanDatabase();
  }
}