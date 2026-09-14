package athl.logistics.athl_logistics.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI getOpenAPI() {
        final Info info = new Info()
                .title("Athl-Logistics APIs")
                .version("0.0.1")
                .description("These APIs expose Athl Logistics endpoints");

        return new OpenAPI().info(info);
    }
}
