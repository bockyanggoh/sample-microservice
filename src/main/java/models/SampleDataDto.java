package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class SampleDataDto {
    private String sampleId;
    private LocalDateTime createdTs;
    private String comment;

    public SampleDataDto(SampleDataEntity entity) {
        this.sampleId = entity.getSampleId();
        this.createdTs = entity.getCreatedTs();
        this.comment = entity.getComment();
    }
}
