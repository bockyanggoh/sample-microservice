package com.cicd.sample.microservice.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class SampleControllerTests {

    private SampleController controller = new SampleController();

    @Test
    void sampleApiReturnsOk() {
        var res = controller.SampleTestApi();
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


}
