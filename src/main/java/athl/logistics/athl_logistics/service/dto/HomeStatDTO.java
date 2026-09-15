package athl.logistics.athl_logistics.service.dto;

import athl.logistics.athl_logistics.models.HomeStat;
import athl.logistics.athl_logistics.models.enums.HomeStatKey;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HomeStatDTO {
    private HomeStatKey key;
    private String label;
    private double value;
    private int decimals;
    private String suffix;

    public HomeStatDTO(HomeStat entity) {
        this.key = entity.getKey();
        this.label = entity.getLabel();
        this.value = entity.getValue();
        this.decimals = entity.getDecimals();
        this.suffix = entity.getSuffix();
    }
}
