package athl.logistics.athl_logistics.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

// Même convention que ServiceStatus : stocké en majuscules, exposé en JSON en minuscules.
public enum JobStatus {
    DRAFT,
    PUBLISHED;

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }

    @JsonCreator
    public static JobStatus fromJson(String value) {
        return JobStatus.valueOf(value.toUpperCase());
    }
}
