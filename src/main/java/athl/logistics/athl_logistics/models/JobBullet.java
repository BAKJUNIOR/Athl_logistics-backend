package athl.logistics.athl_logistics.models;

import athl.logistics.athl_logistics.models.enums.JobBulletKind;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "job_bullets")
public class JobBullet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private JobOffer job;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobBulletKind kind;

    @Column(columnDefinition = "TEXT")
    private String fr;

    @Column(columnDefinition = "TEXT")
    private String en;

    @Column(name = "sort_order")
    private int sortOrder;
}
