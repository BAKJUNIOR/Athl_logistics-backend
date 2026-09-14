package athl.logistics.athl_logistics.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileResponse {

    private String id;
    private String name;
    private Long size;
    private String url;
    private String contentType;
}
