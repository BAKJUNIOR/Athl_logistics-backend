package athl.logistics.athl_logistics.service.dto;

import athl.logistics.athl_logistics.models.JobApplication;
import athl.logistics.athl_logistics.models.enums.SubmissionStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class JobApplicationDTO {
    private Long id;
    private String position;
    private String name;
    private String phone;
    private String email;
    private String experience;
    private String city;
    private String message;
    private String cvUrl;
    private SubmissionStatus status;
    private Instant receivedAt;

    public JobApplicationDTO(JobApplication entity) {
        this.id = entity.getId();
        this.position = entity.getPosition();
        this.name = entity.getName();
        this.phone = entity.getPhone();
        this.email = entity.getEmail();
        this.experience = entity.getExperience();
        this.city = entity.getCity();
        this.message = entity.getMessage();
        this.cvUrl = entity.getCvUrl();
        this.status = entity.getStatus();
        this.receivedAt = entity.getReceivedAt();
    }
}
