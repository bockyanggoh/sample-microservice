package com.cicd.sample.microservice.IntegrationTests.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class SampleControllerIntegrationTests {

    @LocalServerPort
    private int port;
    TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void sampleApiReturnsOk() throws Exception{
        var res = restTemplate.getForEntity("http://localhost:" + port + "/api", String.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
