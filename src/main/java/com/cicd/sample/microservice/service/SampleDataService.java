package com.cicd.sample.microservice.service;

import com.cicd.sample.microservice.repository.SampleDataRepository;
import lombok.extern.slf4j.Slf4j;
import models.SampleDataDto;
import models.SampleDataEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class SampleDataService {
    private SampleDataRepository repository;

    public SampleDataService(SampleDataRepository repository) {
        this.repository = repository;
    }

    public List<SampleDataDto> searchComments(String comment) {
        try {
            var res = repository.findSampleDataEntitiesByCommentContaining(comment);
            if(res.isPresent()) {
                var records = res.get();
                return records.stream()
                        .map(r -> new SampleDataDto(r.getSampleId(), r.getCreatedTs(), r.getComment()))
                        .collect(Collectors.toList());
            }
            return new ArrayList<>();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public Optional<SampleDataDto> findCommentById(String sampleId) {
        try {
            var res = repository.findSampleDataEntityBySampleId(sampleId);
            if(res.isPresent())
                return Optional.of(new SampleDataDto(res.get()));
            return Optional.empty();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<SampleDataEntity> save(SampleDataEntity entity) {
        try {
            var record = repository.saveAndFlush(entity);
            return Optional.of(record);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

}
