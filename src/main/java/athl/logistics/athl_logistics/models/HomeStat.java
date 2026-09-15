package athl.logistics.athl_logistics.models;

import athl.logistics.athl_logistics.models.enums.HomeStatKey;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Un des 3 compteurs animés de l'accueil (et dupliqués sur À propos/Équipe). Jeu de données
 * fixe : les 3 lignes sont seedées une fois (voir DataSeeder) et seules value/decimals/suffix
 * sont modifiables depuis le BO — label reste un texte d'affichage interne au BO, le rendu
 * public garde son propre libellé traduit (voir commentaire côté BO).
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "home_stats")
public class HomeStat {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HomeStatKey key;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private double value;

    @Column(nullable = false)
    private int decimals;

    private String suffix;
}
