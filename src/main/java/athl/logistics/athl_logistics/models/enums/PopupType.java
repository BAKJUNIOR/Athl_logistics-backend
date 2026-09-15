package athl.logistics.athl_logistics.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PopupType {
    IMAGE,
    IMAGE_TEXT,
    VIDEO;

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }

    @JsonCreator
    public static PopupType fromJson(String value) {
        return PopupType.valueOf(value.toUpperCase());
    }
}
