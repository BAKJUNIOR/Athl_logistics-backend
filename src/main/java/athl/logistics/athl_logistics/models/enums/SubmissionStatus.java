package athl.logistics.athl_logistics.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

// Suivi de traitement des soumissions de visiteurs (devis, candidatures) — pas un contenu éditorial.
public enum SubmissionStatus {
    NEW,
    IN_PROGRESS,
    DONE;

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }

    @JsonCreator
    public static SubmissionStatus fromJson(String value) {
        return SubmissionStatus.valueOf(value.toUpperCase());
    }
}
