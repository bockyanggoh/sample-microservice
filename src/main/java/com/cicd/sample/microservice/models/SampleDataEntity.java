package com.cicd.sample.microservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "tbl_sample_data")
@AllArgsConstructor
public class SampleDataEntity {

    @Id
    @Column(length = 36, name = "sample_id")
    private String sampleId;
    @Column(name = "created_ts")
    private LocalDateTime createdTs;
    @Column(name = "comment")
    private String comment;

    public SampleDataEntity(String comment) {
        this.sampleId = UUID.randomUUID().toString();
        this.createdTs = LocalDateTime.now();
        this.comment = comment;
    }

    public SampleDataEntity() {
        this.sampleId = UUID.randomUUID().toString();
        this.createdTs = LocalDateTime.now();
    }

}
