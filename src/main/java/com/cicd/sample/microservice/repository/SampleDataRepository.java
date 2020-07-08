package com.cicd.sample.microservice.repository;

import com.cicd.sample.microservice.models.SampleDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SampleDataRepository extends JpaRepository<SampleDataEntity, String> {

    Optional<SampleDataEntity> findSampleDataEntityBySampleId(String sampleId);
    Optional<List<SampleDataEntity>> findSampleDataEntitiesByCommentContaining(String comment);
}
