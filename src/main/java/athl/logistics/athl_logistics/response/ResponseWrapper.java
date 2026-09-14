package athl.logistics.athl_logistics.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper<T> {

    private boolean success;

    private String message;

    private T data;

    private int statusCode;

    private String errorCode;

    private Long timestamp;

}
