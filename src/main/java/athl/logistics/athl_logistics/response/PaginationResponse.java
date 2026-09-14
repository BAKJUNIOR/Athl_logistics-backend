package athl.logistics.athl_logistics.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaginationResponse<T> {

    private long totalElements;
    private List<T> content;
    private int totalPages;
    private int currentPage;
}
