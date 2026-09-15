package athl.logistics.athl_logistics.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

// Mêmes valeurs que JobDomain côté BO et front (Athl_logistics-BO/domains/jobs,
// Athl_logistics-front/domains/vitrine/domain/enum/job-domain.enum.ts) : "second-oeuvre"
// n'étant pas un identifiant Java valide, on le stocke en SECOND_OEUVRE et on l'expose
// en JSON avec le tiret via @JsonValue/@JsonCreator.
public enum JobDomain {
    CHANTIER,
    SECOND_OEUVRE,
    MOBILITE,
    LOGISTIQUE,
    SUPPORT;

    @JsonValue
    public String toJson() {
        return name().toLowerCase().replace('_', '-');
    }

    @JsonCreator
    public static JobDomain fromJson(String value) {
        return JobDomain.valueOf(value.toUpperCase().replace('-', '_'));
    }
}
