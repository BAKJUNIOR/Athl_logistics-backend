package athl.logistics.athl_logistics.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

// Stocké en base en MAJUSCULES (@Enumerated(EnumType.STRING), non affecté par ces annotations),
// mais exposé en JSON en minuscules ("draft"/"published") pour matcher le type ServiceStatus déjà
// utilisé côté BO et front (Athl_logistics-BO/domains/services, Athl_logistics-front/domains/vitrine).
public enum ServiceStatus {
    DRAFT,
    PUBLISHED;

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }

    @JsonCreator
    public static ServiceStatus fromJson(String value) {
        return ServiceStatus.valueOf(value.toUpperCase());
    }
}
