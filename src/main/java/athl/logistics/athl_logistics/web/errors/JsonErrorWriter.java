package athl.logistics.athl_logistics.web.errors;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

import java.io.IOException;


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
