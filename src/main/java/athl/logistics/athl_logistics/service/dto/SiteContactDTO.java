package athl.logistics.athl_logistics.service.dto;

import athl.logistics.athl_logistics.models.SiteContact;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SiteContactDTO {
    private String phone1;
    private String phone2;
    private String phone3;
    private String address;
    private String facebookUrl;
    private String youtubeUrl;
    private String instagramUrl;
    private String linkedinUrl;

    public SiteContactDTO(SiteContact entity) {
        this.phone1 = entity.getPhone1();
        this.phone2 = entity.getPhone2();
        this.phone3 = entity.getPhone3();
        this.address = entity.getAddress();
        this.facebookUrl = entity.getFacebookUrl();
        this.youtubeUrl = entity.getYoutubeUrl();
        this.instagramUrl = entity.getInstagramUrl();
        this.linkedinUrl = entity.getLinkedinUrl();
    }
}
