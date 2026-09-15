package athl.logistics.athl_logistics.service.dto;

import athl.logistics.athl_logistics.models.Popup;
import athl.logistics.athl_logistics.models.enums.PopupFrequency;
import athl.logistics.athl_logistics.models.enums.PopupLayout;
import athl.logistics.athl_logistics.models.enums.PopupType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class PopupDTO {
    private Long id;
    private String page;
    private boolean active;
    private PopupType type;
    private PopupLayout layout;
    private PopupFrequency frequency;
    private int delayMs;
    private String eyebrow;
    private String title;
    private String text;
    private String image;
    private String video;
    private boolean collectEmail;
    private String ctaLabel;
    private String ctaUrl;
    private Instant updatedAt;

    public PopupDTO(Popup entity) {
        this.id = entity.getId();
        this.page = entity.getPage();
        this.active = entity.isActive();
        this.type = entity.getType();
        this.layout = entity.getLayout();
        this.frequency = entity.getFrequency();
        this.delayMs = entity.getDelayMs();
        this.eyebrow = entity.getEyebrow();
        this.title = entity.getTitle();
        this.text = entity.getText();
        this.image = entity.getImage();
        this.video = entity.getVideo();
        this.collectEmail = entity.isCollectEmail();
        this.ctaLabel = entity.getCtaLabel();
        this.ctaUrl = entity.getCtaUrl();
        this.updatedAt = entity.getUpdatedAt();
    }
}
