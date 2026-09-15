package athl.logistics.athl_logistics.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

// Mêmes valeurs que HomeStatKey côté BO (domains/site-settings) : "sites_delivered" n'étant
// pas un identifiant Java valide, on le stocke en SITES_DELIVERED et on l'expose en JSON
// en minuscules avec underscore via @JsonValue/@JsonCreator (même technique que JobDomain).
public enum HomeStatKey {
    SITES_DELIVERED,
    PROJECT_VALUE,
    ASSET_VALUE;

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }

    @JsonCreator
    public static HomeStatKey fromJson(String value) {
        return HomeStatKey.valueOf(value.toUpperCase());
    }
}
