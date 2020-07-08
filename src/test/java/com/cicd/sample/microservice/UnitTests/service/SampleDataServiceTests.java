package com.cicd.sample.microservice.UnitTests.service;


import com.cicd.sample.microservice.repository.SampleDataRepository;
import com.cicd.sample.microservice.service.SampleDataService;
import models.SampleDataDto;
import models.SampleDataEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class SampleDataServiceTests {

    private SampleDataService service;
    private SampleDataRepository mockRepository;

    @BeforeEach
    void setUp() {
        mockRepository = Mockito.mock(SampleDataRepository.class);
        this.service = new SampleDataService(mockRepository);
    }

    @Test
    void createRecordSuccess() {
        SampleDataEntity entity = new SampleDataEntity("test");
        Mockito.when(mockRepository.saveAndFlush(Mockito.any(SampleDataEntity.class))).thenReturn(entity);
        var response = service.save(entity);
        assertThat(response.isPresent()).isTrue();
        assertThat(response.get()).isEqualTo(entity);
    }

    @Test
    void createRecordFail_Exception() {
        SampleDataEntity entity = new SampleDataEntity("test");
        Mockito.when(mockRepository.saveAndFlush(ArgumentMatchers.isNull())).thenThrow(NullPointerException.class);
        var response = service.save(null);

        assertThat(response.isPresent()).isFalse();
    }

    @Test
    void findRecordByIdSuccess() {

        SampleDataEntity entity = new SampleDataEntity("test");
        Mockito.when(mockRepository.findSampleDataEntityBySampleId(entity.getSampleId())).thenReturn(Optional.of(entity));
        var response = service.findCommentById(entity.getSampleId());

        assertThat(response.isPresent()).isTrue();
        assertThat(response.get()).isEqualTo(new SampleDataDto(entity));
    }

    @Test
    void findRecordByIdFail_NotFound() {
        var response = service.findCommentById("FakeID");
        assertThat(response.isEmpty()).isTrue();
    }

    @Test
    void findRecordByIdFail_Null() {
        Mockito.when(mockRepository.findSampleDataEntityBySampleId(ArgumentMatchers.isNull())).thenThrow(NullPointerException.class);

        var response = service.findCommentById(null);
        assertThat(response.isEmpty()).isTrue();
    }

    @Test
    void findRecordsWithCommentSuccess() {
        String searchText = "test";
        SampleDataEntity entity1 = new SampleDataEntity("test data 1");
        SampleDataEntity entity2 = new SampleDataEntity("test data 2");
        Mockito.when(mockRepository.findSampleDataEntitiesByCommentContaining(searchText)).thenReturn(
                Optional.of(List.of(entity1, entity2))
        );
        var response = service.searchComments(searchText);

        var dtoList = List.of(new SampleDataDto(entity1), new SampleDataDto(entity2));
        assertThat(response.size() > 0);
        assertThat(response).isEqualTo(dtoList);

    }

    @Test
    void findRecordsWithCommentFail_NoComments() {

        String searchText = "test";
        Mockito.when(mockRepository.findSampleDataEntitiesByCommentContaining(searchText)).thenReturn(Optional.empty());
        var response = service.searchComments(searchText);

        assertThat(response.size()).isEqualTo(0);
    }

    @Test
    void findRecordsWithCommentFail_Exception() {

        Mockito.when(mockRepository.findSampleDataEntitiesByCommentContaining(ArgumentMatchers.isNull())).thenThrow(NullPointerException.class);
        var response = service.searchComments(null);

        assertThat(response.size()).isEqualTo(0);
    }




}
