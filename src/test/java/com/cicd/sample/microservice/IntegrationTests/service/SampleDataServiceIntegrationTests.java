package com.cicd.sample.microservice.IntegrationTests.service;

import com.cicd.sample.microservice.models.SampleDataDto;
import com.cicd.sample.microservice.models.SampleDataEntity;
import com.cicd.sample.microservice.repository.SampleDataRepository;
import com.cicd.sample.microservice.service.SampleDataService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class SampleDataServiceIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private SampleDataService service;

    @Autowired
    private SampleDataRepository repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void createRecordSuccess() {
        SampleDataEntity entity = new SampleDataEntity("test");
        service.save(entity);
        var retreived = service.findCommentById(entity.getSampleId());
        assertThat(retreived.isPresent());
        assertThat(retreived.get()).isEqualTo(new SampleDataDto(entity));
    }

    @Test
    public void createRecordFailure_Null() {
        SampleDataEntity entity = null;
        var res = service.save(null);
        assertThat(res.isEmpty());
    }

    @Test
    public void searchCommentsSuccess_FullMatch() {
        var entity1 = new SampleDataEntity("Hello One");
        var entity2 = new SampleDataEntity("Hello Two");
        var entity3 = new SampleDataEntity("Hello Three");
        var list = List.of(new SampleDataDto(entity1), new SampleDataDto(entity2), new SampleDataDto(entity3));

        service.save(entity1);
        service.save(entity2);
        service.save(entity3);

        var res = service.searchComments("Hello");
        assertThat(res).isEqualTo(list);
    }

    @Test
    public void searchCommentsSuccess_PartialMatch() {
        var entity1 = new SampleDataEntity("Blah");
        var entity2 = new SampleDataEntity("Hello Two");
        var entity3 = new SampleDataEntity("Hello Three");
        var list = List.of(new SampleDataDto(entity2), new SampleDataDto(entity3));

        service.save(entity1);
        service.save(entity2);
        service.save(entity3);

        var res = service.searchComments("Hello");
        assertThat(res).isEqualTo(list);
    }

    @Test
    public void findCommentByIdSuccess() {

        var entity1 = new SampleDataEntity("Blah");
        var entity2 = new SampleDataEntity("Hello Two");

        service.save(entity1);
        service.save(entity2);

        var res = service.findCommentById(entity2.getSampleId());
        assertThat(res.isPresent());
        assertThat(res.get()).isEqualTo(new SampleDataDto(entity2));
    }

    @Test
    public void findCommentByIdFailure_Mismatch() {

        var entity1 = new SampleDataEntity("Blah");
        var entity2 = new SampleDataEntity("Hello Two");

        service.save(entity1);

        var res = service.findCommentById(entity2.getSampleId());
        assertThat(res.isEmpty());
    }

    @Test
    public void findCommentByIdFailure_Null() {

        var entity1 = new SampleDataEntity("Blah");
        var entity2 = new SampleDataEntity("Hello Two");

        service.save(entity1);

        var res = service.findCommentById(null);
        assertThat(res.isEmpty());
    }

}
