package athl.logistics.athl_logistics.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PopupFrequency {
    ONCE_PER_VISITOR,
    EVERY_VISIT;

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }

    @JsonCreator
    public static PopupFrequency fromJson(String value) {
        return PopupFrequency.valueOf(value.toUpperCase());
    }
}
