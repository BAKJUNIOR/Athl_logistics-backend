package athl.logistics.athl_logistics.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Domaine métier d'une offre d'emploi (ex: Chantier, Logistique...), géré comme une liste
 * ouverte plutôt qu'un enum fixe : la liste des métiers d'ATHL peut évoluer, un admin doit
 * pouvoir en ajouter un nouveau directement depuis le formulaire d'offre, sans déploiement.
 * Quelques valeurs de départ sont seedées (voir DataSeeder) mais n'importe quel admin peut
 * en créer d'autres.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "job_domains")
public class JobDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le libellé en français est requis")
    @Column(name = "label_fr", nullable = false, unique = true)
    private String labelFr;

    @Column(name = "label_en")
    private String labelEn;
}
