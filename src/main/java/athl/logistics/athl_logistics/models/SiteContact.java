package athl.logistics.athl_logistics.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Coordonnées et réseaux sociaux affichés sur le site vitrine (footer + page Contact).
 * Table singleton : une seule ligne, id figé à 1 (voir DataSeeder), un seul GET, un seul PUT.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "site_contact")
public class SiteContact {

    @Id
    private Long id = 1L;

    private String phone1;
    private String phone2;
    private String phone3;
    private String address;

    @Column(name = "facebook_url")
    private String facebookUrl;

    @Column(name = "youtube_url")
    private String youtubeUrl;

    @Column(name = "instagram_url")
    private String instagramUrl;

    @Column(name = "linkedin_url")
    private String linkedinUrl;
}
