package athl.logistics.athl_logistics.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PopupLayout {
    STACKED,
    IMAGE_LEFT;

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }

    @JsonCreator
    public static PopupLayout fromJson(String value) {
        return PopupLayout.valueOf(value.toUpperCase());
    }
}
