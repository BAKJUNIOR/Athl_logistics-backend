package athl.logistics.athl_logistics.utils;

import com.github.slugify.Slugify;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SlugifyUtils {

    public SlugifyUtils() {
    }

    public static String generate(String Input) {
        String value = String.format("%s , %s", Input, UUID.randomUUID());
        final Slugify slg = Slugify.builder().underscoreSeparator(true).build();
        return slg.slugify(value);
    }
}
