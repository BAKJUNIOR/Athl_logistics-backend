package athl.logistics.athl_logistics.web.errors;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

import java.io.IOException;

// Écrit une erreur au même format que ResponseUtil.error(...)/ResponseWrapper, sans dépendre
// d'un bean ObjectMapper injecté (RestAuthenticationEntryPoint / RestAccessDeniedHandler
// peuvent être sollicités très tôt dans la chaîne de filtres Spring Security ; le payload est
// fixe et trivial, donc pas besoin d'un vrai (dé)sérialiseur JSON ici).
final class JsonErrorWriter {

    private JsonErrorWriter() {
    }

    static void write(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        String json = "{"
                + "\"success\":false,"
                + "\"message\":\"" + escape(message) + "\","
                + "\"statusCode\":" + statusCode + ","
                + "\"timestamp\":" + (System.currentTimeMillis() / 1000)
                + "}";
        response.getWriter().write(json);
    }

    private static String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
