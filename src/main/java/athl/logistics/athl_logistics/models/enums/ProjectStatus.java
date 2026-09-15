package athl.logistics.athl_logistics.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

// Même convention que ServiceStatus/JobStatus : stocké en majuscules, exposé en JSON en minuscules.
public enum ProjectStatus {
    DRAFT,
    PUBLISHED;

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }

    @JsonCreator
    public static ProjectStatus fromJson(String value) {
        return ProjectStatus.valueOf(value.toUpperCase());
    }
}
