package com.cicd.sample.microservice.service;


import com.cicd.sample.microservice.repository.SampleDataRepository;
import models.SampleDataEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

public class SampleDataServiceTests {

    private SampleDataService service;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createRecordSuccess() {
        SampleDataEntity entity = new SampleDataEntity("test");

        SampleDataRepository repository = Mockito.mock(SampleDataRepository.class);
        this.service = new SampleDataService(repository);
        Mockito.when(repository.saveAndFlush(Mockito.any(SampleDataEntity.class))).thenReturn(entity);

        var response = service.save(entity);
        assertThat(response.isPresent()).isTrue();
        assertThat(response.get()).isEqualTo(entity);

    }
}
