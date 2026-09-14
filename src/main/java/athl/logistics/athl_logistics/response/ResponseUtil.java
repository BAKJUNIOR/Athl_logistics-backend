package athl.logistics.athl_logistics.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ResponseUtil {

    private static final String NULL_DATA = null;
    private static final Integer SUCCESS_STATUS_CODE = 200;

    private ResponseUtil() {
        throw new IllegalStateException("Cannot be instantiated");
    }

    public static <T> ResponseWrapper<T> success(T data) {
        ResponseWrapper<T> response = new ResponseWrapper<>();
        response.setStatusCode(SUCCESS_STATUS_CODE);
        response.setTimestamp(timestamp());
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    public static <T> ResponseWrapper<T> success() {
        return success(null);
    }

    public static ResponseWrapper<String> error(int code, String message) {
        ResponseWrapper<String> response = new ResponseWrapper<>();
        response.setStatusCode(code);
        response.setTimestamp(timestamp());
        response.setMessage(message);
        response.setData(NULL_DATA);
        return response;
    }

    public static <T> ResponseWrapper<T> error(int code, String message, Class<T> type) {
        ResponseWrapper<T> response = new ResponseWrapper<>();
        response.setStatusCode(code);
        response.setTimestamp(timestamp());
        response.setMessage(message);
        response.setData(null);
        return response;
    }

    private static long timestamp() {
        return System.currentTimeMillis() / 1000;
    }
}
