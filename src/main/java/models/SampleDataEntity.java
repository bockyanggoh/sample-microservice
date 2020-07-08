package models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class SampleDataEntity {

    @Id
    @Column(length = 36)
    private String sampleId;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdTs;
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
