package athl.logistics.athl_logistics.service.dto;

import athl.logistics.athl_logistics.models.QuoteRequest;
import athl.logistics.athl_logistics.models.enums.SubmissionStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class QuoteRequestDTO {
    private Long id;
    private String serviceLabel;
    private String name;
    private String phone;
    private String description;
    private List<String> attachments;
    private SubmissionStatus status;
    private Instant receivedAt;

    public QuoteRequestDTO(QuoteRequest entity) {
        this.id = entity.getId();
        this.serviceLabel = entity.getServiceLabel();
        this.name = entity.getName();
        this.phone = entity.getPhone();
        this.description = entity.getDescription();
        this.attachments = new ArrayList<>(entity.getAttachments());
        this.status = entity.getStatus();
        this.receivedAt = entity.getReceivedAt();
    }
}
